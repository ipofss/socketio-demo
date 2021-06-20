package com.wbs.socketiodemo.socketio;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息管理服务
 *
 * @author: wangbingshuai
 * @create: 2019-11-29 17:25
 **/
@Component
@Slf4j
public class SocketIoServer implements InitializingBean, DisposableBean {

    private SocketIOServer server;

    @Autowired
    private SocketIoEvent socketIoEvent;

    @Override
    public void afterPropertiesSet() throws Exception {
        Configuration config = new Configuration();
        config.setPort(8888);
        SocketConfig socketConfig = config.getSocketConfig();
        // 避免netty-socketio服务关闭并启动后，报Address already in use
        socketConfig.setReuseAddress(true);
        server = new SocketIOServer(config);
        server.addConnectListener(client -> log.info(client.getRemoteAddress() + "新客户端接入"));
        server.addListeners(socketIoEvent);
        server.start();
        log.info("===== 启动SocketIO =====");
    }

    @Override
    public void destroy() throws Exception {
        server.stop();
        log.info("===== 关闭SocketIO =====");
    }
}
