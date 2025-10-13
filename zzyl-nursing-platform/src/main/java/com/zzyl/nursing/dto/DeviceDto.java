package com.zzyl.nursing.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "设备注册参数")
public class DeviceDto {

    private Long id;

    /** 备注 */
    private String remark;

    /**
     * 设备标识码，通常使用IMEI、MAC地址或Serial No作为node_id
     */
    @ApiModelProperty(value = "设备标识码", required = true)
    private String nodeId;

    @ApiModelProperty(value = "设备id")
    public String iotId;

    @ApiModelProperty(value = "产品的id")
    public String productKey;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "位置名称回显字段")
    private String deviceDescription;

    @ApiModelProperty(value = "位置类型 0 老人 1位置")
    private Integer locationType;

    @ApiModelProperty(value = "绑定位置")
    private Long bindingLocation;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "物理位置类型 0楼层 1房间 2床位")
    private Integer physicalLocationType;
}