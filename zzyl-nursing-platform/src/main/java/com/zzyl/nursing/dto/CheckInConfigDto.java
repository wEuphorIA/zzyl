package com.zzyl.nursing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author itcast
 */
@ApiModel(description = "入住配置信息")
@Data
public class CheckInConfigDto {

    @ApiModelProperty(value = "入住开始时间,格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "入住结束时间,格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @ApiModelProperty(value = "费用开始时间,格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime feeStartDate;

    @ApiModelProperty(value = "费用结束时间,格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime feeEndDate;

    @ApiModelProperty(value = "护理等级ID")
    private Long nursingLevelId;

    @ApiModelProperty(value = "护理等级名称")
    private String nursingLevelName;

    @ApiModelProperty(value = "床位Id")
    private Long bedId;

    @ApiModelProperty(value = "押金金额")
    private BigDecimal deposit;

    @ApiModelProperty(value = "护理费用")
    private BigDecimal nursingFee;

    @ApiModelProperty(value = "床位费用")
    private BigDecimal bedFee;

    @ApiModelProperty(value = "其他费用")
    private BigDecimal otherFees;

    @ApiModelProperty(value = "医保支付")
    private BigDecimal insurancePayment;

    @ApiModelProperty(value = "政府补贴")
    private BigDecimal governmentSubsidy;

    @ApiModelProperty("房间ID")
    private Long roomId;

    @ApiModelProperty(value = "楼层id")
    private Long floorId;

    @ApiModelProperty(value = "楼层名称")
    private String floorName;

    @ApiModelProperty(value = "房间编号")
    private String code;

}
