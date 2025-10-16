package com.zzyl.nursing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 报警数据分页查询请求参数
 */
@Data
@ApiModel("报警数据分页查询请求参数")
public class AlertDataQueryDto {

    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码", example = "1", required = true)
    private Integer pageNum;

    @NotNull(message = "页面大小不能为空")
    @ApiModelProperty(value = "页面大小", example = "10", required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "设备名称（精确搜索）", example = "体温监测仪001")
    private String deviceName;

    @ApiModelProperty(value = "开始报警时间", example = "2024-01-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束报警时间", example = "2024-01-31 23:59:59")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "状态，0：待处理，1：已处理", example = "0", allowableValues = "0,1")
    private Integer status;
}