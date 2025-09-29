package com.zzyl.nursing.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzyl.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/9/29 下午8:17 */
@Data
public class NursingLevelVo {

    /** 主键ID */
    @ApiModelProperty("主键ID" )
    private Long id;

    /** 等级名称 */
    @Excel(name = "等级名称" )

    @ApiModelProperty("等级名称" )
    private String name;

    /** 护理计划ID */
    @Excel(name = "护理计划ID" )

    @ApiModelProperty("护理计划ID" )
    private Long lplanId;

    /** 护理费用 */
    @Excel(name = "护理费用" )

    @ApiModelProperty("护理费用" )
    private BigDecimal fee;

    /** 状态（0：禁用，1：启用） */
    @Excel(name = "状态" , readConverterExp = "0=：禁用，1：启用" )

    @ApiModelProperty("状态（0：禁用，1：启用）" )
    private Integer status;

    /** 等级说明 */
    @Excel(name = "等级说明" )
    @ApiModelProperty("等级说明")
    private String description;

    @ApiModelProperty("护理计划")
    private String planName;

    /** 创建者 */
    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /** 更新者 */
    @ApiModelProperty(value = "更新者")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;
}
