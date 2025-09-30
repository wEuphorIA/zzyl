package com.zzyl.nursing.domain;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 护理项目对象 nursing_project
 * 
 * @author alexis
 * @date 2024-12-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("护理项目实体")
public class NursingProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    // 编号
    @ApiModelProperty("编号")
    private Long id;

    // 名称
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    // 排序号
    @Excel(name = "排序号")
    @ApiModelProperty("排序号")
    private Integer orderNo;

    // 单位
    @Excel(name = "单位")
    @ApiModelProperty("单位")
    private String unit;

    // 价格
    @Excel(name = "价格")
    @ApiModelProperty("价格")
    private BigDecimal price;

    // 图片
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String image;

    // 护理要求
    @Excel(name = "护理要求")
    @ApiModelProperty("护理要求")
    private String nursingRequirement;

    // 状态（0：禁用，1：启用）
    @Excel(name = "状态", readConverterExp = "0=：禁用，1：启用")
    @ApiModelProperty("状态（0：禁用，1：启用）")
    private Integer status;

}
