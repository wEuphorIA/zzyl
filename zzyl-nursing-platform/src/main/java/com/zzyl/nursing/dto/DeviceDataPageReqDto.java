package com.zzyl.nursing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ApiModel("设备数据分页查询请求模型")
public class DeviceDataPageReqDto {

    @ApiModelProperty(value = "设备名称", required = false)
    private String deviceName;

    @ApiModelProperty(value = "功能ID", required = false)
    private String functionId;

    @ApiModelProperty(value = "开始时间", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Integer pageNum;

    @ApiModelProperty(value = "页面大小", required = true, example = "10")
    private Integer pageSize;

}