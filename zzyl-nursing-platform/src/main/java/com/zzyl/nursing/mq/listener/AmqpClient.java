package com.zzyl.nursing.mq.listener;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;


import com.zzyl.framework.config.properties.HuaWeiIotConfigProperties;
import com.zzyl.nursing.mq.vo.IotMsgNotifyData;
import com.zzyl.nursing.service.IDeviceDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.*;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;
import org.apache.qpid.jms.transports.TransportOptions;
import org.apache.qpid.jms.transports.TransportSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author itcast
 */
@Slf4j
@Component
public class AmqpClient implements ApplicationRunner {

    @Autowired
    private HuaWeiIotConfigProperties huaWeiIotConfigProperties;

    //业务处理异步线程池，线程池参数可以根据您的业务特点调整，或者您也可以用其他异步方式处理接收到的消息。
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private IDeviceDataService deviceDataService;

    //控制台服务端订阅中消费组状态页客户端ID一栏将显示clientId参数。
    //建议使用机器UUID、MAC地址、IP等唯一标识等作为clientId。便于您区分识别不同的客户端。
    private static String clientId;

    static {
        try {
            clientId = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * spring容器启动后执行
     * @param args incoming application arguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
    }

    public void start() throws Exception {
        //参数说明，请参见AMQP客户端接入说明文档。
        for (int i = 0; i < huaWeiIotConfigProperties.getConnectionCount(); i++) {
            //创建amqp连接
            Connection connection = getConnection();

            //加入监听者
            ((JmsConnection) connection).addConnectionListener(myJmsConnectionListener);
            // 创建会话。
            // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()。
            // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）。
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();

            // 创建Receiver连接。
            MessageConsumer consumer = newConsumer(session, connection, huaWeiIotConfigProperties.getQueueName());
            consumer.setMessageListener(messageListener);
        }

        log.info("amqp  is started successfully, and will exit after server shutdown ");
    }

    /**
     * 创建amqp连接
     *
     * @return amqp连接
     */
    private Connection getConnection() throws Exception {
        String connectionUrl = generateConnectUrl();
        JmsConnectionFactory cf = new JmsConnectionFactory(connectionUrl);
        // 信任服务端
        TransportOptions to = new TransportOptions();
        to.setTrustAll(true);
        cf.setSslContext(TransportSupport.createJdkSslContext(to));
        String userName = "accessKey=" + huaWeiIotConfigProperties.getAccessKey();
        cf.setExtension(JmsConnectionExtensions.USERNAME_OVERRIDE.toString(), (connection, uri) -> {
            // IoTDA的userName组成格式如下：“accessKey=${accessKey}|timestamp=${timestamp}”
            String newUserName = userName;
            if (connection instanceof JmsConnection) {
                newUserName = ((JmsConnection) connection).getUsername();
            }
            return newUserName + "|timestamp=" + System.currentTimeMillis();
        });

        // 创建连接。
        return cf.createConnection(userName, huaWeiIotConfigProperties.getAccessCode());
    }

    /**
     * 生成amqp连接地址
     *
     * @return amqp连接地址
     */
    public String generateConnectUrl() {
        String uri = MessageFormat.format("{0}://{1}:{2}",
                (huaWeiIotConfigProperties.isUseSsl() ? "amqps" : "amqp"),
                huaWeiIotConfigProperties.getHost(),
                String.valueOf(huaWeiIotConfigProperties.getPort()));
        Map<String, String> uriOptions = new HashMap<>();
        uriOptions.put("amqp.vhost", huaWeiIotConfigProperties.getVhost());
        uriOptions.put("amqp.idleTimeout", String.valueOf(huaWeiIotConfigProperties.getIdleTimeout()));
        uriOptions.put("amqp.saslMechanisms", huaWeiIotConfigProperties.getSaslMechanisms());

        Map<String, String> jmsOptions = new HashMap<>();
        jmsOptions.put("jms.prefetchPolicy.queuePrefetch", String.valueOf(huaWeiIotConfigProperties.getQueuePrefetch()));
        if (CharSequenceUtil.isNotBlank(clientId)) {
            jmsOptions.put("jms.clientID", clientId);
        } else {
            jmsOptions.put("jms.clientID", UUID.randomUUID().toString());
        }
        jmsOptions.put("failover.reconnectDelay", String.valueOf(huaWeiIotConfigProperties.getReconnectDelay()));
        jmsOptions.put("failover.maxReconnectDelay", String.valueOf(huaWeiIotConfigProperties.getMaxReconnectDelay()));
        if (huaWeiIotConfigProperties.getMaxReconnectAttempts() > 0) {
            jmsOptions.put("failover.maxReconnectAttempts", String.valueOf(huaWeiIotConfigProperties.getMaxReconnectAttempts()));
        }
        if (huaWeiIotConfigProperties.getExtendedOptions() != null) {
            for (Map.Entry<String, String> option : huaWeiIotConfigProperties.getExtendedOptions().entrySet()) {
                if (option.getKey().startsWith("amqp.") || option.getKey().startsWith("transport.")) {
                    uriOptions.put(option.getKey(), option.getValue());
                } else {
                    jmsOptions.put(option.getKey(), option.getValue());
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(uriOptions.entrySet().stream()
                .map(option -> MessageFormat.format("{0}={1}", option.getKey(), option.getValue()))
                .collect(Collectors.joining("&", "failover:(" + uri + "?", ")")));
        stringBuilder.append(jmsOptions.entrySet().stream()
                .map(option -> MessageFormat.format("{0}={1}", option.getKey(), option.getValue()))
                .collect(Collectors.joining("&", "?", "")));
        return stringBuilder.toString();
    }

    /**
     * 创建消费者
     *
     * @param session    session
     * @param connection amqp连接
     * @param queueName  队列名称
     * @return 消费者
     */
    public MessageConsumer newConsumer(Session session, Connection connection, String queueName) throws Exception {
        if (connection == null || !(connection instanceof JmsConnection) || ((JmsConnection) connection).isClosed()) {
            throw new Exception("create consumer failed,the connection is disconnected.");
        }

        return session.createConsumer(new JmsQueue(queueName));
    }

    private final MessageListener messageListener = message -> {
        try {
            //异步处理收到的消息，确保onMessage函数里没有耗时逻辑
            threadPoolTaskExecutor.submit(() -> processMessage(message));
        } catch (Exception e) {
            log.error("submit task occurs exception ", e);
        }
    };

    /**
     * 在这里处理您收到消息后的具体业务逻辑。
     */
    private void processMessage(Message message) {
        String contentStr;
        try {
            contentStr = message.getBody(String.class);
            String topic = message.getStringProperty("topic");
            String messageId = message.getStringProperty("messageId");
            log.info("receive message,\n topic = {},\n messageId = {},\n content = {}", topic, messageId, contentStr);

            //将消息转换为json格式，数据为空，直接返回
            JSONObject jsonMsg = JSONUtil.parseObj(contentStr);

            //取出消息中的notify_data字段，数据为空，直接返回
            JSONObject jsonNotifyData = jsonMsg.getJSONObject("notify_data");
            if (ObjectUtil.isEmpty(jsonMsg)) {
                return;
            }
            //json格式转换为对象，如果属性上报为空，程序结束
            IotMsgNotifyData iotMsgNotifyData = JSONUtil.toBean(jsonNotifyData, IotMsgNotifyData.class);
            if (ObjectUtil.isEmpty(iotMsgNotifyData.getBody()) || ObjectUtil.isEmpty(iotMsgNotifyData.getBody().getServices())) {
                return;
            }
            deviceDataService.batchInsertDeviceData(iotMsgNotifyData);
        } catch (JMSException e) {
            throw new RuntimeException("服务器错误");
        }

    }

    private final JmsConnectionListener myJmsConnectionListener = new JmsConnectionListener() {
        /**
         * 连接成功建立。
         */
        @Override
        public void onConnectionEstablished(URI remoteURI) {
            log.info("onConnectionEstablished, remoteUri:{}", remoteURI);
        }

        /**
         * 尝试过最大重试次数之后，最终连接失败。
         */
        @Override
        public void onConnectionFailure(Throwable error) {
            log.error("onConnectionFailure, {}", error.getMessage());
        }

        /**
         * 连接中断。
         */
        @Override
        public void onConnectionInterrupted(URI remoteURI) {
            log.info("onConnectionInterrupted, remoteUri:{}", remoteURI);
        }

        /**
         * 连接中断后又自动重连上。
         */
        @Override
        public void onConnectionRestored(URI remoteURI) {
            log.info("onConnectionRestored, remoteUri:{}", remoteURI);
        }

        @Override
        public void onInboundMessage(JmsInboundMessageDispatch envelope) {
        }

        @Override
        public void onSessionClosed(Session session, Throwable cause) {
        }

        @Override
        public void onConsumerClosed(MessageConsumer consumer, Throwable cause) {
        }

        @Override
        public void onProducerClosed(MessageProducer producer, Throwable cause) {
        }
    };
}