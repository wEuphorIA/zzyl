package com.zzyl.nursing.mq.vo;

import lombok.Data;

import java.util.List;

/**
 * amqp消息-body部分
 *
 * @author itcast
 * @create 2024/1/16 10:00
 **/
@Data
public class IotMsgBody {
    /**
     * 服务列表
     */
    private List<IotMsgService> services;
}
