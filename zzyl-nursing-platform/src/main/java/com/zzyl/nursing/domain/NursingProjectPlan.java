package com.zzyl.nursing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 护理计划和项目关联对象 nursing_project_plan
 * 
 * @author alexis
 * @date 2024-12-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("护理计划和项目关联实体")
public class NursingProjectPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    // 主键id
    @ApiModelProperty("主键id")
    private Long id;

    // 计划id
    @Excel(name = "计划id")
    @ApiModelProperty("计划id")
    private Long planId;

    // 项目id
    @Excel(name = "项目id")
    @ApiModelProperty("项目id")
    private Long projectId;

    // 计划执行时间
    @Excel(name = "计划执行时间")
    @ApiModelProperty("计划执行时间")
    private String executeTime;

    // 执行周期 0 天 1 周 2月
    @Excel(name = "执行周期 0 天 1 周 2月")
    @ApiModelProperty("执行周期 0 天 1 周 2月")
    private Integer executeCycle;

    // 执行频次
    @Excel(name = "执行频次")
    @ApiModelProperty("执行频次")
    private Integer executeFrequency;

}
