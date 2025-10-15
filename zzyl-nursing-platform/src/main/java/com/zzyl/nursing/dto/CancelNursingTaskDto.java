package com.zzyl.nursing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 取消护理任务请求参数
 */
@Data
@ApiModel("取消护理任务请求参数")
public class CancelNursingTaskDto {
    
    @ApiModelProperty(value = "护理任务ID", example = "123", required = true)
    private Long taskId;
    
    @ApiModelProperty(value = "取消原因", example = "老人临时外出", required = true)
    private String reason;
}