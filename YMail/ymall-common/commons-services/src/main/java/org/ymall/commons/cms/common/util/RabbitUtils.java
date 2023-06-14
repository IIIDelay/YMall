

package org.ymall.commons.cms.common.util;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.Serializable;

/**
 * RabbitMQ工具类
 *
 * @since 2021/03/07 10:34
 **/
public class RabbitUtils {

    /**
     * 设置消息的延迟时间（单位毫秒）
     *
     * @param delay 正整数
     * @return MessagePostProcessor
     */
    public static MessagePostProcessor setDelay(Integer delay) {
        Assert.isTrue(delay >= 0, "参数【delay】必须为正整数");
        return message -> {
            message.getMessageProperties().setDelay(delay);
            return message;
        };
    }

    /**
     * 创建CorrelationData工具方法
     *
     * @param id ID
     * @return CorrelationData
     */
    public static CorrelationData correlationData(Serializable id) {
        Assert.notNull(id, "参数【id】不能为空");
        if (id instanceof Number) {
            return new CorrelationData(String.valueOf(id));
        } else if (id instanceof String) {
            return new CorrelationData((String) id);
        }
        return null;
    }

    /**
     * 手动确认消息
     *
     * @param channel {@link Channel}
     * @param message {@link Message}
     */
    public static void basicAck(Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
