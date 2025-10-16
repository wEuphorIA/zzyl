package com.zzyl.nursing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 报警数据视图对象
 */
@Data
@ApiModel("报警数据信息VO")
public class AlertDataVo {

    @ApiModelProperty(value = "主键ID", example = "3")
    private Long id;

    @ApiModelProperty(value = "物联网设备ID", example = "847XFy3JE4CcQk6SpHMHj0rk00")
    private String iotId;

    @ApiModelProperty(value = "设备名称", example = "watch01")
    private String deviceName;

    @ApiModelProperty(value = "设备昵称", example = "手表1号")
    private String nickname;

    @ApiModelProperty(value = "产品密钥", example = "j0rkvUZBtRu")
    private String productKey;

    @ApiModelProperty(value = "产品名称", example = "智能定位报警手表")
    private String productName;

    @ApiModelProperty(value = "功能ID", example = "HeartRate")
    private String functionId;

    @ApiModelProperty(value = "接入位置", example = "刘备")
    private String accessLocation;

    @ApiModelProperty(value = "位置类型", example = "0")
    private Integer locationType;

    @ApiModelProperty(value = "物理位置类型", example = "-1")
    private Integer physicalLocationType;

    @ApiModelProperty(value = "设备描述")
    private String deviceDescription;

    @ApiModelProperty(value = "数据值", example = "94")
    private String dataValue;

    @ApiModelProperty(value = "报警规则ID", example = "57")
    private Long alertRuleId;

    @ApiModelProperty(value = "报警原因", example = "HeartRate>=80.0,持续了1周期，就报警")
    private String alertReason;

    @ApiModelProperty(value = "处理结果")
    private String processingResult;

    @ApiModelProperty(value = "处理人ID")
    private Long processorId;

    @ApiModelProperty(value = "处理人姓名")
    private String processorName;

    @ApiModelProperty(value = "处理时间")
    private LocalDateTime processingTime;

    @ApiModelProperty(value = "报警类型", example = "0")
    private Integer type;

    @ApiModelProperty(value = "状态（0：待处理，1：已处理）", example = "0")
    private Integer status;

    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间", example = "2024-09-27 23:08:44")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间", example = "2024-09-27 15:08:50")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}