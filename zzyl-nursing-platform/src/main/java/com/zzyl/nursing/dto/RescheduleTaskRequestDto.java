package com.zzyl.nursing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RescheduleTaskRequestDto {
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedServerTime;  // 新计划时间
    
    private Long taskId;           // 原任务ID
}