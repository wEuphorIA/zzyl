package com.zzyl.nursing.mq.vo;

import lombok.Data;

/**
 * amqp订阅消息
 *
 * @author itcast
 */
@Data
public class IotMsgNotifyData {

    /**
     * amqp消息-header部分
     */
    private IotMsgHeader header;

    /**
     * amqp消息-body部分
     */
    private IotMsgBody body;
}