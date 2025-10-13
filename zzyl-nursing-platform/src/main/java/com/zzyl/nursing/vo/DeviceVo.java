package com.zzyl.nursing.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/13 下午7:13 */
@Data
public class DeviceVo {

    private String functionId;

    /**
     * 属性值
     */
    private Object value;

    private LocalDateTime eventTime;
}
