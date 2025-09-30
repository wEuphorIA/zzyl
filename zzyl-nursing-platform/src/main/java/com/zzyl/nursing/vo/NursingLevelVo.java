package com.zzyl.nursing.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.zzyl.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 护理等级对象 nursing_level
 * 
 * @author ruoyi
 * @date 2024-10-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("护理等级实体")
public class NursingLevelVo {
    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
    * 等级名称
    */
    @ApiModelProperty(value = "等级名称")
    private String name;

    /**
    * 护理计划ID
    */
    @ApiModelProperty(value = "护理计划ID")
    private Long lplanId;

    /**
    * 护理费用
    */
    @ApiModelProperty(value = "护理费用")
    private BigDecimal fee;

    /**
    * 状态（0：禁用，1：启用）
    */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
    * 等级说明
    */
    @ApiModelProperty(value = "等级说明")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "关联护理计划名称")
    private String planName;
}
