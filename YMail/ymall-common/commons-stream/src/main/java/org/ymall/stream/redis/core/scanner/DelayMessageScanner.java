/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis.core.scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.ymall.stream.redis.core.constants.MessageConstants;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class DelayMessageScanner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final Executor executor;

    private Thread scanThread;

    private AtomicBoolean isStop = new AtomicBoolean(false);

    public DelayMessageScanner(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper, Executor executor) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.executor = executor;
    }

    public void start() {
        int millis = 1000;

        scanThread = new Thread() {
            @Override
            public void run() {

                while (!isStop.get()) {
                    // 只获取第一条数据, 只获取不会移除数据
                    long max = LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli() + 1000;
                    Set<String> values = redisTemplate.opsForZSet().rangeByScore(MessageConstants.DELAY_QUEUE_KEY, 0, max);

                    if (CollectionUtils.isEmpty(values)) {
                        try {
                            if (!Thread.interrupted()) {
                                Thread.sleep(millis);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        continue;
                    }

                    for (String value : values) {
                        executor.execute(() -> {
                            // 抢到数据，发送延时消息
                            if (redisTemplate.opsForZSet().remove(MessageConstants.DELAY_QUEUE_KEY, value) > 0) {
                                MapType mapType = objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, String.class);
                                try {
                                    Map<String, String> message = objectMapper.readValue(value, mapType);
                                    String topic = message.get(MessageConstants.MSG_TOPIC);
                                    redisTemplate.opsForStream().add(topic, message);
                                } catch (JsonProcessingException e) {
                                    log.error("", e);
                                }
                            }
                        });
                    }
                }
            }
        };

        scanThread.setDaemon(true);
        scanThread.setName("redis-delaying-queue-scanner-executor-thread");
        scanThread.start();
    }

    public void stop() {
        isStop.set(true);
        // interrupt and wait
        if (scanThread != null) {
            scanThread.interrupt();
            try {
                scanThread.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
