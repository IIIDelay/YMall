/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.ymall.stream.redis.core.constants.MessageConstants;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.Map;

public class MessagePublisher {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public MessagePublisher(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(String topic, Message message) {

        publish(topic, message, null);
    }

    public void publish(String topic, Message message, LocalDateTime delayTime) {
        LocalDateTime nowTime = LocalDateTime.now();

        Map<String, String> rawMessage = new LinkedHashMap<>();
        rawMessage.put(MessageConstants.MSG_TOPIC, topic);
        rawMessage.put(MessageConstants.MSG_BODY, message.getBody());
        rawMessage.put(MessageConstants.MSG_TIME, MessageConstants.MSG_TIME_FORMATTER.format(nowTime));
        rawMessage.put(MessageConstants.MSG_DELAY_TIME, MessageConstants.MSG_TIME_FORMATTER.format(delayTime != null ? delayTime : nowTime));

        if (delayTime != null && nowTime.isBefore(delayTime)) {
            long millis = delayTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
            try {
                String value = objectMapper.writeValueAsString(rawMessage);
                redisTemplate.opsForZSet().add(MessageConstants.DELAY_QUEUE_KEY, value, millis);
            } catch (JsonProcessingException e) {
                log.error("", e);
            }
            return;
        }

        redisTemplate.opsForStream().add(topic, rawMessage);
    }

}
