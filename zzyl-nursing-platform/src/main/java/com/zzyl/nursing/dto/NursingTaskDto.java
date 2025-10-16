package com.zzyl.nursing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 护理任务数据传输对象
 */
@Data
public class NursingTaskDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedServerTime;  // 预计服务时间

    private String mark;                 // 执行记录

    private Long taskId;                // 任务ID

    private String taskImage;            // 任务图片URL

}