package com.wbs.socketiodemo.socketio;

import lombok.Data;

/**
 * 消息体
 *
 * @author: wangbingshuai
 * @create: 2019-11-29 17:14
 **/
@Data
public class SocketIoMessage {
    /**
     * 发送者
     */
    private String from;
    /**
     * 接受者
     */
    private String to;
    /**
     * 消息内容
     */
    private String content;
}
