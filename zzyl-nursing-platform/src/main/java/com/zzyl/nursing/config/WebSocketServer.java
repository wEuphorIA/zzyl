package com.zzyl.nursing.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.zzyl.common.exception.base.BaseException;
import com.zzyl.nursing.vo.AlertNotifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@EnableWebSocket
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    private static Map<String, Session> sessionMap = new HashMap<>();

    /**
     * 连接建立时触发
     *
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        log.info("有客户端连接到了服务器 , {}", sid);
        sessionMap.put(sid, session);
    }

    /**
     * 服务端接收到消息时触发
     *
     * @param session
     * @param message
     * @param sid
     */
    @OnMessage
    public void onMessage(Session session, String message, @PathParam("sid") String sid) {
        log.info("接收到了客户端 {} 发来的消息 : {}", sid, message);
    }

    /**
     * 连接关闭时触发
     *
     * @param session
     * @param sid
     */
    @OnClose
    public void onClose(Session session, @PathParam("sid") String sid) {
        System.out.println("连接断开:" + sid);
        sessionMap.remove(sid);
    }

    /**
     * 通信发生错误时触发
     *
     * @param session
     * @param sid
     * @param throwable
     */
    @OnError
    public void onError(Session session, @PathParam("sid") String sid, Throwable throwable) {
        System.out.println("出现错误:" + sid);
        throwable.printStackTrace();
    }

    /**
     * 广播消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessageToAll(String message) throws IOException {
        Collection<Session> sessions = sessionMap.values();
        if (!CollectionUtils.isEmpty(sessions)) {
            for (Session session : sessions) {
                //服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 发送websocket消息给指定消费者
     *
     * @param alertNotifyVo 报警消息
     * @param userIds   报警数据map
     * @throws IOException io异常
     */
    public void sendMessageToConsumer(AlertNotifyVo alertNotifyVo, Collection<Long> userIds) {
        // 如果消费者为空，程序结束
        if (CollUtil.isEmpty(userIds)) {
            return;
        }

        // 如果websoket客户端为空，程序结束
        if (ObjectUtil.isEmpty(sessionMap)) {
            return;
        }

        // 遍历消费者，发送消息
        // key为消息接收人id，value为报警数据id
        userIds.forEach(userId -> {
            // 获取该消费者的websocket连接，如果不存在，跳出本次循环
            Session session = sessionMap.get(String.valueOf(userId));
            if (ObjectUtil.isEmpty(session)) {
                return;
            }
            // 获取该消费者的websocket连接，并发送消息
            try {
                session.getBasicRemote().sendText(JSONUtil.toJsonStr(alertNotifyVo));
            } catch (IOException e) {
                throw new BaseException("websocket推送消息失败");
            }
        });
    }

}