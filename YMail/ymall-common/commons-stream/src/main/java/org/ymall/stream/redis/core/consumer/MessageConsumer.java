/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis.core.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.util.ObjectUtils;
import org.ymall.stream.redis.Message;
import org.ymall.stream.redis.core.anno.MessageSubscribeDefinition;
import org.ymall.stream.redis.core.constants.MessageConstants;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class MessageConsumer implements StreamListener<String, MapRecord<String, String, String>> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final MessageSubscribeDefinition subscribeDefinition;

    public MessageConsumer(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper,
                           MessageSubscribeDefinition subscribeDefinition) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.subscribeDefinition = subscribeDefinition;
    }

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        // 消息ID
        RecordId messageId = message.getId();
        String stream = message.getStream();
        Map<String, String> rawMessageMap = message.getValue();

        Object rawMessage = new Message(rawMessageMap.get(MessageConstants.MSG_BODY));
        try {
            if (String.class.isAssignableFrom(subscribeDefinition.getMethod().getParameterTypes()[0])) {
                rawMessage = objectMapper.writeValueAsString(rawMessage);
            }

            subscribeDefinition.getMethod().invoke(subscribeDefinition.getBean(), rawMessage);
            log.info("Message queue，consumer message success. id: {}, topic: {}, group: {}",
                messageId.getValue(), stream, subscribeDefinition.getGroup());
            redisTemplate.opsForStream().acknowledge(stream, subscribeDefinition.getGroup(), messageId);
        } catch (InvocationTargetException ex) {
            log.error("Message queue，consumer message fail. id: {}, topic: {}, group: {}",
                messageId.getValue(), stream, subscribeDefinition.getGroup());
            Throwable targetEx = ex.getTargetException();
            if (targetEx instanceof DataAccessException) {
                throw (DataAccessException) targetEx;
            } else {
                throw new RuntimeException(
                    "Consumer method '" + subscribeDefinition.getBean().getClass().getName() + "#"
                        + subscribeDefinition.getMethod().getName() + "' threw exception", targetEx);
            }
        } catch (Throwable ex) {
            throw new RuntimeException(
                "Failed to invoke target method '" + subscribeDefinition.getBean().getClass().getName() + "#"
                    + subscribeDefinition.getMethod().getName() + "' with arguments " + ObjectUtils
                    .nullSafeToString(message), ex);
        }
    }
}

