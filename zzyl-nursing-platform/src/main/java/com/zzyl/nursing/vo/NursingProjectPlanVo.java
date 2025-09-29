package com.zzyl.nursing.vo;

import com.zzyl.common.annotation.Excel;
import lombok.Data;

@Data
public class NursingProjectPlanVo {

    /** 项目id */
    private String projectId;

    /** $column.columnComment */
    private Long id;

    /** 计划id */
    @Excel(name = "计划id")
    private Long planId;

    /** 计划执行时间 */
    @Excel(name = "计划执行时间")
    private String executeTime;

    /** 执行周期 0 天 1 周 2月 */
    @Excel(name = "执行周期 0 天 1 周 2月")
    private String executeCycle;

    /** 执行频次 */
    @Excel(name = "执行频次")
    private Long executeFrequency;
}