package com.zzyl.framework.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author itheima
 */

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "huaweicloud")
public class HuaWeiIotConfigProperties {

    /**
     * 访问Key
     */
    private String ak;

    /**
     * 访问秘钥
     */
    private String sk;

    /**
     * 区域id
     */
    private String regionId;

    /**
     * 应用侧https接入地址
     */
    private String endpoint;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 应用侧amqp接入地址
     */
    private String host;

    /**
     * amqp连接端口
     */
    private int port = 5671;

    /**
     * amqp接入凭证键值
     */
    private String accessKey;

    /**
     * amqp接入凭证密钥
     */
    private String accessCode;

    // 指定单个进程启动的连接数
    // 单个连接消费速率有限，请参考使用限制，最大64个连接
    // 连接数和消费速率及rebalance相关，建议每500QPS增加一个连接
    //可根据实际情况自由调节，目前测试和正式环境资源有限，限制更改为4
    private int connectionCount = 4;

    /**
     * 队列名称
     */
    private String queueName;

    /**
     * 开门命令所属服务id
     */
    private String smartDoorServiceId;

    /**
     * 开门记录属性
     */
    private String doorOpenPropertyName;

    /**
     * 开门命令
     */
    private String doorOpenCommandName;

    /**
     * 设置临时密码命令
     */
    private String passwordSetCommandName;

    /**
     * 仅支持true
     */
    private boolean useSsl = true;

    /**
     * IoTDA仅支持default
     */
    private String vhost = "default";

    /**
     * IoTDA仅支持PLAIN
     */
    private String saslMechanisms = "PLAIN";

    /**
     * true: SDK自动ACK（默认）
     * false:收到消息后，需要手动调用message.acknowledge()
     */
    private boolean isAutoAcknowledge = true;

    /**
     * 重连时延（ms）
     */
    private long reconnectDelay = 3000L;

    /**
     * 最大重连时延（ms）,随着重连次数增加重连时延逐渐增加
     */
    private long maxReconnectDelay = 30 * 1000L;

    /**
     * 最大重连次数,默认值-1，代表没有限制
     */
    private long maxReconnectAttempts = -1;

    /**
     * 空闲超时，对端在这个时间段内没有发送AMQP帧则会导致连接断开。默认值为30000。单位：毫秒。
     */
    private long idleTimeout = 30 * 1000L;

    /**
     * The values below control how many messages the remote peer can send to the client and be held in a pre-fetch buffer for each consumer instance.
     */
    private int queuePrefetch = 1000;

    /**
     * 扩展参数
     */
    private Map<String, String> extendedOptions;
}