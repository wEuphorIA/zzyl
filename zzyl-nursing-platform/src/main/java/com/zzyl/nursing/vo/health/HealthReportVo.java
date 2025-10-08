package com.zzyl.nursing.vo.health;

import lombok.Data;

import java.util.List;

/**
 * 体检报告
 * @author itheima
 */
@Data
public class HealthReportVo {

    /**
     * 体检日期
     */
    private String totalCheckDate;
    /**
     * 健康评估
     */
    private HealthAssessmentVo healthAssessment;
    /**
     * 风险分布
     */
    private RiskDistributionVo riskDistribution;
    /**
     * 异常数据列表
     */
    private List<AbnormalDataVo> abnormalData;

    /**
     * 健康系统分值
     */
    private SystemScore systemScore;

    /**
     * 综合总结
     */
    private String summarize;
}
