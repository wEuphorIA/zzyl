package com.zzyl.nursing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 告警数据处理结果 DTO
 */
@Data
public class AlertProcessResultDTO {

    private Long id;

    @NotBlank(message = "处理结果不能为空")
    private String processingResult;  // 处理结果

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime processingTime;


}