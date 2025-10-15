package com.zzyl.nursing.dto;

import lombok.Data;

/**
 护理任务数据传输对象（极简版）
 */
@Data
public class NursingTaskDto {

    private String estimatedServerTime;  // 预计服务时间
    private String mark;                 // 执行记录
    private Long taskId;                // 任务ID
    private String taskImage;            // 任务图片URL

}