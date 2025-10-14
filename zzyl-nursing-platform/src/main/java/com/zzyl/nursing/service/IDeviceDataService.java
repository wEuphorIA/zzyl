package com.zzyl.nursing.service;

import java.util.List;

import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.nursing.domain.DeviceData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzyl.nursing.dto.DeviceDataPageReqDto;
import com.zzyl.nursing.mq.vo.IotMsgNotifyData;

/**
 设备数据Service接口

 @author ruoyi
 @date 2025-10-14 */
public interface IDeviceDataService extends IService<DeviceData> {
    /**
     查询设备数据

     @param id 设备数据主键
     @return 设备数据
     */
    public DeviceData selectDeviceDataById(Long id);

    /**
     查询设备数据列表

     @param deviceDataPageReqDto 设备数据
     @return 设备数据集合
     */
    public TableDataInfo selectDeviceDataList(DeviceDataPageReqDto deviceDataPageReqDto);

    /**
     新增设备数据

     @param deviceData 设备数据
     @return 结果
     */
    public int insertDeviceData(DeviceData deviceData);

    /**
     修改设备数据

     @param deviceData 设备数据
     @return 结果
     */
    public int updateDeviceData(DeviceData deviceData);

    /**
     批量删除设备数据

     @param ids 需要删除的设备数据主键集合
     @return 结果
     */
    public int deleteDeviceDataByIds(Long[] ids);

    /**
     删除设备数据信息

     @param id 设备数据主键
     @return 结果
     */
    public int deleteDeviceDataById(Long id);

    /**
     批量保存数据

     @param iotMsgNotifyData
     */
    void batchInsertDeviceData(IotMsgNotifyData iotMsgNotifyData);
}
