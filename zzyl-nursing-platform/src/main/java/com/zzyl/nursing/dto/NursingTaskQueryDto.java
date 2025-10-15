package com.zzyl.nursing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 护理任务查询请求参数
 */
@Data
@ApiModel("护理任务查询请求参数")
public class NursingTaskQueryDto {
    
    @ApiModelProperty(value = "老人姓名", example = "张三")
    private String elderName;
    
    @ApiModelProperty(value = "开始时间", example = "2024-01-01T00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    @ApiModelProperty(value = "结束时间", example = "2024-12-31T23:59:59")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    @ApiModelProperty(value = "护理员ID", example = "1")
    private Long nurseId;
    
    @ApiModelProperty(value = "护理项目ID", example = "1")
    private Long projectId;
    
    @ApiModelProperty(
        value = "状态(1待执行 2已执行 3已关闭)", 
        example = "1",
        allowableValues = "1,2,3"
    )
    private Integer status;
    
    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页显示条数", example = "10")
    private Integer pageSize = 10;
}