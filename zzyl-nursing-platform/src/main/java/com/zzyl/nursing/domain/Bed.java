package com.zzyl.nursing.domain;

import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 床位对象 bed
 * 
 * @author ruoyi
 * @date 2024-04-26
 */
@Data
@ApiModel("床位实体对象")
public class Bed extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 床位ID */
    @ApiModelProperty(value = "床位ID")
    private Long id;

    /** 床位编号 */
    @Excel(name = "床位编号")
    @ApiModelProperty(value = "床位编号")
    private String bedNumber;

    /** 床位状态: 未入住0, 已入住1 入住申请中2 */
    @Excel(name = "床位状态: 未入住0, 已入住1 入住申请中2")
    @ApiModelProperty(value = "床位状态: 未入住0, 已入住1 入住申请中2")
    private Integer bedStatus;

    /** 床位号 */
    @Excel(name = "床位号")
    @ApiModelProperty(value = "床位号")
    private Long sort;

    /** 房间ID */
    @Excel(name = "房间ID")
    @ApiModelProperty(value = "房间ID")
    private Long roomId;

}
