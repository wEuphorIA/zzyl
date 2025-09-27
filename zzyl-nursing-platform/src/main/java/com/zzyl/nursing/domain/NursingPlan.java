package com.zzyl.nursing.domain;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 护理计划对象 nursing_plan
 *
 * @author Euphoria
 * @date 2025-09-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NursingPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 排序号 */
    @Excel(name = "排序号")
    private Integer sortNo;

    /** 名称 */
    @Excel(name = "名称")
    private String planName;

    /** 状态 0禁用 1启用 */
    @Excel(name = "状态 0禁用 1启用")
    private Integer status;

}