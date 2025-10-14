package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzyl.nursing.mq.vo.HourlyDataVo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

import com.zzyl.nursing.domain.DeviceData;
import org.apache.ibatis.annotations.Param;
import springfox.bean.validators.plugins.parameter.MinMaxAnnotationPlugin;

/**
 设备数据Mapper接口

 @author ruoyi
 @date 2025-10-14 */
@Mapper
public interface DeviceDataMapper extends BaseMapper<DeviceData> {

    /**
     查询设备数据

     @param id 设备数据主键
     @return 设备数据
     */
    public DeviceData selectDeviceDataById(Long id);

    /**
     查询设备数据列表

     @param deviceData 设备数据
     @return 设备数据集合
     */
    public List<DeviceData> selectDeviceDataList(DeviceData deviceData);

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
     删除设备数据

     @param id 设备数据主键
     @return 结果
     */
    public int deleteDeviceDataById(Long id);

    /**
     批量删除设备数据

     @param ids 需要删除的数据主键集合
     @return 结果
     */
    public int deleteDeviceDataByIds(Long[] ids);

    List<HourlyDataVo> queryDeviceDataListByDay(@Param("iotId") String iotId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("functionId") String functionId);

    List<HourlyDataVo> queryDeviceDataListByWeek(@Param("iotId") String iotId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("functionId") String functionId);
}
