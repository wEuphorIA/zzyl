package com.zzyl.nursing.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzyl.common.constant.CacheConstants;
import com.zzyl.common.constant.HttpStatus;
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.common.utils.DateUtils;
import com.zzyl.common.utils.StringUtils;
import com.zzyl.nursing.domain.Device;
import com.zzyl.nursing.dto.DeviceDataPageReqDto;
import com.zzyl.nursing.mapper.DeviceMapper;
import com.zzyl.nursing.mq.vo.IotMsgNotifyData;
import com.zzyl.nursing.mq.vo.IotMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.DeviceDataMapper;
import com.zzyl.nursing.domain.DeviceData;
import com.zzyl.nursing.service.IDeviceDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;

import static com.zzyl.common.constant.CacheConstants.IOT_DEVICE_LAST_DATA;

/**
 设备数据Service业务层处理

 @author ruoyi
 @date 2025-10-14 */
@Service
public class DeviceDataServiceImpl extends ServiceImpl<DeviceDataMapper, DeviceData> implements IDeviceDataService {

    @Autowired
    private DeviceDataMapper deviceDataMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     查询设备数据

     @param id 设备数据主键
     @return 设备数据
     */
    @Override
    public DeviceData selectDeviceDataById(Long id) {
        return deviceDataMapper.selectById(id);
    }

    /**
     查询设备数据列表

     @param deviceDataPageReqDto 设备数据
     @return 设备数据
     */
    @Override
    public TableDataInfo selectDeviceDataList(DeviceDataPageReqDto deviceDataPageReqDto) {

        LambdaQueryWrapper<DeviceData> lambdaQueryWrapper =  new LambdaQueryWrapper<>();
        Page<DeviceData> page = new Page(deviceDataPageReqDto.getPageNum(),deviceDataPageReqDto.getPageSize());
        //模糊查询设备名称
        if(StringUtils.isNotEmpty(deviceDataPageReqDto.getDeviceName())){
            lambdaQueryWrapper.eq(DeviceData::getDeviceName,deviceDataPageReqDto.getDeviceName());
        }
        //精确查询功能名称
        if (StringUtils.isNotEmpty(deviceDataPageReqDto.getFunctionId())) {
            lambdaQueryWrapper.eq(DeviceData::getFunctionId, deviceDataPageReqDto.getFunctionId());
        }
        //时间范围查询
        if(ObjectUtils.isNotEmpty(deviceDataPageReqDto.getStartTime()) && ObjectUtils.isNotEmpty(deviceDataPageReqDto.getEndTime())){
            lambdaQueryWrapper.between(DeviceData::getAlarmTime,deviceDataPageReqDto.getStartTime(),deviceDataPageReqDto.getEndTime());
        }

        //分页查询
        page = page(page,lambdaQueryWrapper);

        //封装分页对象
        return getTableDataInfo(page);
    }

    private static TableDataInfo getTableDataInfo(Page<DeviceData> page) {
        TableDataInfo tableData = new TableDataInfo();
        tableData.setCode(HttpStatus.SUCCESS);
        tableData.setMsg("查询成功");
        tableData.setRows(page.getRecords());
        tableData.setTotal(page.getTotal());
        return tableData;
    }

    /**
     新增设备数据

     @param deviceData 设备数据
     @return 结果
     */
    @Override
    public int insertDeviceData(DeviceData deviceData) {
        return deviceDataMapper.insert(deviceData);
    }

    /**
     修改设备数据

     @param deviceData 设备数据
     @return 结果
     */
    @Override
    public int updateDeviceData(DeviceData deviceData) {
        return deviceDataMapper.updateById(deviceData);
    }

    /**
     批量删除设备数据

     @param ids 需要删除的设备数据主键
     @return 结果
     */
    @Override
    public int deleteDeviceDataByIds(Long[] ids) {
        return deviceDataMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     删除设备数据信息

     @param id 设备数据主键
     @return 结果
     */
    @Override
    public int deleteDeviceDataById(Long id) {
        return deviceDataMapper.deleteById(id);
    }

    /**
     批量保存数据@param iotMsgNotifyData
     */
    @Override
    @Transactional
    public void batchInsertDeviceData(IotMsgNotifyData iotMsgNotifyData) {

        //判断设备是否存在
        String deviceId = iotMsgNotifyData.getHeader().getDeviceId();
        Device device = deviceMapper.selectOne(new LambdaQueryWrapper<Device>().eq(Device::getIotId, deviceId));

        if (Objects.isNull(device)) {
            return;
        }
        List<DeviceData> list = new ArrayList<>();

        List<IotMsgService> services = iotMsgNotifyData.getBody().getServices();

        for (IotMsgService service : services) {

            //所有数据都已经装入到map中
            Map<String, Object> properties = service.getProperties();
            if (ObjectUtil.isEmpty(properties)){
                continue;
            }

            //处理上报时间日期
            LocalDateTime eventTime =  LocalDateTimeUtil.parse(service.getEventTime(), "yyyyMMdd'T'HHmmss'Z'");
            //日期时区转换
            LocalDateTime alarmTime = eventTime.atZone(ZoneId.from(ZoneOffset.UTC))
                    .withZoneSameInstant(ZoneId.of("Asia/Shanghai"))
                    .toLocalDateTime();

            properties.forEach((key, value) -> {
                DeviceData deviceData = BeanUtil.toBean(device, DeviceData.class);
                deviceData.setId(null);
                deviceData.setDataValue(value.toString());
                deviceData.setAlarmTime(alarmTime);
                deviceData.setFunctionId(key);
                deviceData.setAccessLocation(device.getBindingLocation());
                list.add(deviceData);
            });
        }
        saveBatch(list);

        redisTemplate.opsForHash().put(IOT_DEVICE_LAST_DATA,deviceId, JSON.toJSONString(list));
    }
}
