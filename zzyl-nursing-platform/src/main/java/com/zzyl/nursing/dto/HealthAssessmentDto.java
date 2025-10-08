package com.zzyl.nursing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("健康评估")
public class HealthAssessmentDto {

    /**
     * 老人姓名
     */
    @ApiModelProperty(value = "老人姓名",required = true)
    private String elderName;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号",required = true)
    private String idCard;

    /**
     * 体检机构
     */
    @ApiModelProperty(value = "体检机构",required = true)
    private String physicalExamInstitution;

    /**
     * 体检报告URL链接
     */
    @ApiModelProperty(value = "体检报告URL链接",required = true)
    private String physicalReportUrl;

}