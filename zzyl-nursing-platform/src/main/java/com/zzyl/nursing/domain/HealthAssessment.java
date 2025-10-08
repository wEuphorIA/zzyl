package com.zzyl.nursing.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 健康评估对象 health_assessment
 * 
 * @author ruoyi
 * @date 2024-10-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("健康评估实体")
public class HealthAssessment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
    * 老人姓名
    */
    @Excel(name = "老人姓名")
    @ApiModelProperty(value = "老人姓名")
    private String elderName;

    /**
    * 身份证号
    */
    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    /**
    * 出生日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthDate;

    /**
    * 年龄
    */
    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
    * 性别(0:男，1:女)
    */
    @Excel(name = "性别(0:男，1:女)")
    @ApiModelProperty(value = "性别(0:男，1:女)")
    private Integer gender;

    /**
    * 健康评分
    */
    @Excel(name = "健康评分")
    @ApiModelProperty(value = "健康评分")
    private String healthScore;

    /**
    * 危险等级(健康, 提示, 风险, 危险, 严重危险)
    */
    @Excel(name = "危险等级(健康, 提示, 风险, 危险, 严重危险)")
    @ApiModelProperty(value = "危险等级(健康, 提示, 风险, 危险, 严重危险)")
    private String riskLevel;

    /**
    * 是否建议入住(0:建议，1:不建议)
    */
    @Excel(name = "是否建议入住(0:建议，1:不建议)")
    @ApiModelProperty(value = "是否建议入住(0:建议，1:不建议)")
    private Integer suggestionForAdmission;

    /**
    * 推荐护理等级
    */
    @Excel(name = "推荐护理等级")
    @ApiModelProperty(value = "推荐护理等级")
    private String nursingLevelName;

    /**
    * 入住情况(0:已入住，1:未入住)
    */
    @Excel(name = "入住情况(0:已入住，1:未入住)")
    @ApiModelProperty(value = "入住情况(0:已入住，1:未入住)")
    private Integer admissionStatus;

    /**
    * 总检日期
    */
    @Excel(name = "总检日期")
    @ApiModelProperty(value = "总检日期")
    private String totalCheckDate;

    /**
    * 体检机构
    */
    @Excel(name = "体检机构")
    @ApiModelProperty(value = "体检机构")
    private String physicalExamInstitution;

    /**
    * 体检报告URL链接
    */
    @Excel(name = "体检报告URL链接")
    @ApiModelProperty(value = "体检报告URL链接")
    private String physicalReportUrl;

    /**
    * 评估时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "评估时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "评估时间")
    private LocalDateTime assessmentTime;

    /**
    * 报告总结
    */
    @Excel(name = "报告总结")
    @ApiModelProperty(value = "报告总结")
    private String reportSummary;

    /**
    * 疾病风险
    */
    @Excel(name = "疾病风险")
    @ApiModelProperty(value = "疾病风险")
    private String diseaseRisk;

    /**
    * 异常分析
    */
    @Excel(name = "异常分析")
    @ApiModelProperty(value = "异常分析")
    private String abnormalAnalysis;

    /**
    * 健康系统分值
    */
    @Excel(name = "健康系统分值")
    @ApiModelProperty(value = "健康系统分值")
    private String systemScore;

}
