package com.wbs.socketiodemo.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息事件处理类
 *
 * @author: wangbingshuai
 * @create: 2019-11-29 17:20
 **/
@Component
@Slf4j
public class SocketIoEvent {

    private static Map<String, SocketIOClient> fromMap = new ConcurrentHashMap<>();

    @OnConnect
    public void OnConnect(SocketIOClient client) {
        log.info("新连接加入：" + client.getSessionId());
    }

    @OnEvent("sendMsg")
    public void sendMsg(SocketIOClient client, SocketIoMessage message) {
        fromMap.putIfAbsent(message.getFrom(), client);
        client.sendEvent("receiveMsg", message);
        SocketIOClient toClient = fromMap.get(message.getTo());
        if (toClient != null) {
            toClient.sendEvent("receiveMsg", message);
        }
    }

    @OnDisconnect
    public void OnDisconnect(SocketIOClient client) {
        log.info("一个连接关闭了：" + client.getSessionId());
    }
}
