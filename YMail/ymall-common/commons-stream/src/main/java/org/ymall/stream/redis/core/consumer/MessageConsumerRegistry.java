/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis.core.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamReadRequest;
import org.ymall.stream.redis.core.anno.MessageSubscribeDefinition;
import org.ymall.stream.redis.core.handler.MessageErrorHandler;
import org.ymall.stream.redis.core.ware.MessageSubscribeWare;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;

public class MessageConsumerRegistry {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private StreamMessageListenerContainer<String, MapRecord<String, String, String>> container;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final MessageSubscribeWare messageSubscribeWare;
    private final Executor executor;

    public MessageConsumerRegistry(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper,
            MessageSubscribeWare messageSubscribeWare, Executor executor) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.messageSubscribeWare = messageSubscribeWare;
        this.executor = executor;
    }

    public void start() {
        log.info("Register message queue consumer ...");
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        // 一次最多获取多少条消息
                        .batchSize(10)
                        .serializer(new StringRedisSerializer())
                        // 运行 Stream 的 poll task
                        .executor(executor)
                        // Stream 中没有消息时，阻塞多长时间，需要比 `spring.redis.timeout` 的时间小
                        .pollTimeout(Duration.ZERO)
                        // 获取消息的过程或获取到消息给具体的消息者处理的过程中，发生了异常的处理
                        .errorHandler(new MessageErrorHandler())
                        .build();

        container = StreamMessageListenerContainer.create(redisTemplate.getConnectionFactory(), options);

        Map<String, MessageSubscribeDefinition> subscribeMap = messageSubscribeWare.getAll();
        for (Entry<String, MessageSubscribeDefinition> subscribe : subscribeMap.entrySet()) {
            MessageSubscribeDefinition definition = subscribe.getValue();

            createGroupIfAbsent(definition.getTopic(), definition.getGroup());

            StreamReadRequest<String> streamReadRequest = StreamReadRequest
                    .builder(StreamOffset.create(definition.getTopic(), ReadOffset.lastConsumed()))
                    .cancelOnError(cancelOnError -> false)
                    .build();

            container.register(streamReadRequest, new MessageConsumer(redisTemplate, objectMapper, definition));
        }
        log.info("Register message queue consumer completed.");

        log.info("Starting message queue container ...");
        container.start();
        log.info("Started message queue container completed.");
    }

    public void stop() {
        log.info("Stopping message queue container ...");
        container.stop();
        log.info("Stopped message queue container completed.");
    }

    private void createGroupIfAbsent(String topic, String group) {
        StreamOperations<String, String, String> streamOperations = redisTemplate.opsForStream();
        if (ifAbsent(topic, group)) {
            streamOperations.createGroup(topic, ReadOffset.from("0-0"), group);
        }
    }

    private boolean ifAbsent(String topic, String group) {
        return !redisTemplate.hasKey(topic)
                || redisTemplate.opsForStream().groups(topic).stream().noneMatch(it -> group.equals(it.groupName()));
    }

    private String localhostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("", e);
        }
        return "127.0.0.1";
    }


}
