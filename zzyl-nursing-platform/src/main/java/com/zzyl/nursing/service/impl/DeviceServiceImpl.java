package com.zzyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.ListProductsRequest;
import com.huaweicloud.sdk.iotda.v5.model.ListProductsResponse;
import com.huaweicloud.sdk.iotda.v5.model.ProductSummary;
import com.zzyl.common.exception.base.BaseException;
import com.zzyl.common.utils.DateUtils;
import com.zzyl.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
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
    private RedisTemplate<String,String> redisTemplate;


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
        } catch (Exception e){
            log.error("物联网接口 - 获取产品列表失败",e);
            throw new BaseException("物联网接口 - 查询产品，同步失败");
        }

        if (response.getHttpStatusCode() != 200) {
            log.error("物联网接口 - 获取产品列表失败{}",response);
            throw new BaseException("物联网接口 - 查询产品，同步失败");
        }
        List<ProductSummary> products = response.getProducts();

       redisTemplate.opsForValue().set(ALL_PRODUCT_KEY, JSON.toJSONString(products));
    }

    @Override
    public List<ProductSummary> allProduct() {

        //从redis中查询数据
        String json = redisTemplate.opsForValue().get(ALL_PRODUCT_KEY);

        if (StringUtils.isBlank(json)){
            return null;
        }

        return (List<ProductSummary>) JSON.parse(redisTemplate.opsForValue().get(ALL_PRODUCT_KEY));
    }
}
