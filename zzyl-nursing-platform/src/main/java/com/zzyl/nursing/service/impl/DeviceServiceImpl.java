package com.zzyl.nursing.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;




import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.*;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.exception.base.BaseException;
import com.zzyl.common.utils.StringUtils;
import com.zzyl.nursing.dto.DeviceDto;
import com.zzyl.nursing.vo.DeviceDetailVo;
import com.zzyl.nursing.vo.DeviceVo;
import com.zzyl.nursing.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.DeviceMapper;
import com.zzyl.nursing.domain.Device;
import com.zzyl.nursing.service.IDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import static com.zzyl.common.constant.CacheConstants.ALL_PRODUCT_KEY;

/**
 设备Service业务层处理

 @author Euphoria
 @date 2025-10-13 */
@Service
@Slf4j
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private IoTDAClient client;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     查询设备

     @param id 设备主键
     @return 设备
     */
    @Override
    public Device selectDeviceById(Long id) {
        return deviceMapper.selectById(id);
    }

    /**
     查询设备列表

     @param device 设备
     @return 设备
     */
    @Override
    public List<Device> selectDeviceList(Device device) {
        return deviceMapper.selectDeviceList(device);
    }

    /**
     新增设备

     @param device 设备
     @return 结果
     */
    @Override
    public int insertDevice(Device device) {
        return deviceMapper.insert(device);
    }

    /**
     修改设备

     @param device 设备
     @return 结果
     */
    @Override
    public int updateDevice(Device device) {
        return deviceMapper.updateById(device);
    }

    /**
     批量删除设备

     @param ids 需要删除的设备主键
     @return 结果
     */
    @Override
    public int deleteDeviceByIds(Long[] ids) {
        return deviceMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     删除设备信息

     @param id 设备主键
     @return 结果
     */
    @Override
    public int deleteDeviceById(Long id) {
        return deviceMapper.deleteById(id);
    }

    /**
     同步产品列表
     */
    @Override
    public void syncProductList() {

        ListProductsRequest listProductsRequest = new ListProductsRequest();
        listProductsRequest.setLimit(50);

        ListProductsResponse response;
        try {
            response = client.listProducts(listProductsRequest);
        } catch (Exception e) {
            log.error("物联网接口 - 获取产品列表失败", e);
            throw new BaseException("物联网接口 - 查询产品，同步失败");
        }

        if (response.getHttpStatusCode() != 200) {
            log.error("物联网接口 - 获取产品列表失败{}", response);
            throw new BaseException("物联网接口 - 查询产品，同步失败");
        }
        List<ProductSummary> products = response.getProducts();

        redisTemplate.opsForValue().set(ALL_PRODUCT_KEY, JSON.toJSONString(products));
    }

    @Override
    public List<ProductVo> allProduct() {

        //从redis中查询数据
        String json = redisTemplate.opsForValue().get(ALL_PRODUCT_KEY);

        if (StringUtils.isBlank(json)) {
            return Collections.emptyList();
        }

        return JSON.parseArray(json, ProductVo.class);
    }

    @Override
    public void register(DeviceDto deviceDto) {
        //1. 判断设备名称是否重复
        Long count = lambdaQuery().eq(Device::getDeviceName, deviceDto.getDeviceName()).count();

        if (count > 0) {
            throw new BaseException("设备名称重复");
        }

        //2. 判断设备标识是否重复
        count = lambdaQuery().eq(Device::getNodeId, deviceDto.getNodeId()).count();

        if (count > 0) {
            throw new BaseException("设备标识重复");
        }

        //判断如果是随身设备，将物理位置设置为-1
        if (deviceDto.getLocationType() == 0) {
            deviceDto.setPhysicalLocationType(-1);
        }

        //3. 判断同一位置是否绑定了相同的产品
        count = lambdaQuery()
                .eq(Device::getBindingLocation, deviceDto.getBindingLocation())
                .eq(Device::getProductKey, deviceDto.getProductKey())
                .eq(Device::getLocationType, deviceDto.getLocationType())
                .eq(Device::getPhysicalLocationType, deviceDto.getPhysicalLocationType())
                .count();

        if (count > 0) {
            throw new BaseException("同一位置已绑定相同产品");
        }
        //4. 注册设备到华为iot平台
        //随机生成密钥
        String secret = UUID.randomUUID().toString().replaceAll("-", "");
        AddDeviceRequest addDeviceRequest = new AddDeviceRequest();
        AddDevice addDevice = new AddDevice();

        addDevice.setNodeId(deviceDto.getNodeId());
        addDevice.setProductId(deviceDto.getProductKey());
        addDevice.setDeviceName(deviceDto.getDeviceName());

        addDevice.setAuthInfo(new AuthInfo().withSecret(secret));

        addDeviceRequest.withBody(addDevice);

        AddDeviceResponse response = null;
        try {
            response = client.addDevice(addDeviceRequest);
        } catch (Exception e) {
            log.error("注册设备失败", e);
            throw new BaseException("注册设备失败");
        }

        if (response.getHttpStatusCode() != 201) {
            log.error("注册设备失败{}", response);
            throw new BaseException("注册设备失败");
        }
        Device device = new Device();
        BeanUtils.copyProperties(deviceDto, device);
        device.setBindingLocation(String.valueOf(deviceDto.getBindingLocation()));
        device.setIotId(response.getDeviceId());
        device.setSecret(secret);
        save(device);
    }

    @Override
    public DeviceDetailVo getInfo(String iotId) {

        Device device = lambdaQuery().eq(Device::getIotId, iotId).one();

        DeviceDetailVo deviceVo = BeanUtil.toBean(device, DeviceDetailVo.class);

        ShowDeviceResponse response = null;

        try {
            response = client.showDevice(new ShowDeviceRequest().withDeviceId(iotId));
        } catch (Exception e) {
            log.error("查询设备详情失败", e);
            throw new BaseException("查询设备详情失败");
        }

        deviceVo.setDeviceStatus(response.getStatus());

        String activeTimeStr = response.getActiveTime();
        if(StringUtils.isNotEmpty(activeTimeStr)){
            LocalDateTime activeTime = LocalDateTimeUtil.parse(activeTimeStr, DatePattern.UTC_MS_PATTERN);
            //日期时区转换
            activeTime = activeTime.atZone(ZoneId.from(ZoneOffset.UTC))
                    .withZoneSameInstant(ZoneId.of("Asia/Shanghai"))
                    .toLocalDateTime();
            deviceVo.setActiveTime(activeTime);
        }

        return deviceVo;
    }

    @Override
    public List<DeviceVo> queryServiceProperties(String iotId) {

        ShowDeviceShadowRequest request = new ShowDeviceShadowRequest();
        request.setDeviceId(iotId);
        ShowDeviceShadowResponse response = client.showDeviceShadow(request);
        if (response.getHttpStatusCode() != 200) {
            throw new BaseException("物联网接口 - 查询设备上报数据，调用失败");
        }
        List<DeviceShadowData> shadow = response.getShadow();
        if(CollUtil.isEmpty(shadow)){
            return Collections.emptyList();
        }
        // 解析JSON对象
        Object yourObject = shadow.get(0).getReported().getProperties();
        JSONObject jsonObject = (JSONObject) JSON.toJSON(yourObject);

        List<DeviceVo> deviceList = new ArrayList<>();

        // 处理上报时间日期
        LocalDateTime activeTime = LocalDateTime.parse(
                shadow.get(0).getReported().getEventTime(),
                DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
        );

        // 日期时区转换（UTC → 上海时区）
        LocalDateTime eventTime = activeTime.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Shanghai"))
                .toLocalDateTime();

        // 遍历JSON对象并转换为DeviceVo列表
        jsonObject.forEach((key, value) -> {
            DeviceVo deviceVo = new DeviceVo();
            deviceVo.setFunctionId(key);
            deviceVo.setValue(value);
            deviceVo.setEventTime(eventTime);
            deviceList.add(deviceVo);
        });

        return deviceList;
    }
}
