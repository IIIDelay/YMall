/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.util;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * {@link WebSocketUtils}工具类 用于处理socket会话
 **/
public class WebSocketUtils {
    /**
     * 定义存储session的容器
     */
    private final static CopyOnWriteArraySet<WebSocketSession> SESSION_SETS = new CopyOnWriteArraySet<>();

    private WebSocketUtils() {
    }

    /**
     * 添加会话
     */
    public static void add(WebSocketSession session) {
        SESSION_SETS.add(session);
    }

    /**
     * 移除会话
     */
    public static void remove(WebSocketSession session) {
        SESSION_SETS.remove(session);
    }

    /**
     * （群）发送消息
     */
    public static void sendMessage(Object msg) {
        TextMessage textMessage = new TextMessage(JacksonUtils.writeValueAsString(msg));
        try {
            for (WebSocketSession session : SESSION_SETS) {
                session.sendMessage(textMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
