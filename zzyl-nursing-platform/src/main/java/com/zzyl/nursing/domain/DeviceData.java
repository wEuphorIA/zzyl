package com.zzyl.nursing.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 设备数据对象 device_data
 * 
 * @author ruoyi
 * @date 2025-10-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("设备数据实体")
public class DeviceData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    // 告警规则ID，自增主键
    @ApiModelProperty("告警规则ID，自增主键")
    private Long id;

    // 设备名称
    @Excel(name = "设备名称")
    @ApiModelProperty("设备名称")
    private String deviceName;

    // 设备ID
    @Excel(name = "设备ID")
    @ApiModelProperty("设备ID")
    private String iotId;

    // 所属产品的key
    @Excel(name = "所属产品的key")
    @ApiModelProperty("所属产品的key")
    private String productKey;

    // 产品名称
    @Excel(name = "产品名称")
    @ApiModelProperty("产品名称")
    private String productName;

    // 功能名称
    @Excel(name = "功能名称")
    @ApiModelProperty("功能名称")
    private String functionId;

    // 接入位置
    @Excel(name = "接入位置")
    @ApiModelProperty("接入位置")
    private String accessLocation;

    // 位置类型 0：随身设备 1：固定设备
    @Excel(name = "位置类型 0：随身设备 1：固定设备")
    @ApiModelProperty("位置类型 0：随身设备 1：固定设备")
    private Integer locationType;

    // 物理位置类型 0楼层 1房间 2床位
    @Excel(name = "物理位置类型 0楼层 1房间 2床位")
    @ApiModelProperty("物理位置类型 0楼层 1房间 2床位")
    private Integer physicalLocationType;

    // 位置备注
    @Excel(name = "位置备注")
    @ApiModelProperty("位置备注")
    private String deviceDescription;

    // 数据值
    @Excel(name = "数据值")
    @ApiModelProperty("数据值")
    private String dataValue;

    // 数据上报时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "数据上报时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("数据上报时间")
    private LocalDateTime alarmTime;

}
