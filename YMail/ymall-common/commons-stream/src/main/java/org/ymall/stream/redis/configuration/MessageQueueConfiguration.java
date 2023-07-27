/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.ymall.stream.redis.MessagePublisher;
import org.ymall.stream.redis.core.consumer.MessageConsumerRegistry;
import org.ymall.stream.redis.core.executor.MessageExecutors;
import org.ymall.stream.redis.core.scanner.DelayMessageScanner;
import org.ymall.stream.redis.core.ware.MessageSubscribeWare;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

@Configuration
@ConditionalOnClass({RedisTemplate.class})
public class MessageQueueConfiguration {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Bean
    public MessageSubscribeWare messageSubscribeWare() {
        return new MessageSubscribeWare();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnClass({MessageSubscribeWare.class})
    public MessageConsumerRegistry messageQueueRegistry(MessageSubscribeWare messageSubscribeWare) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        Executor executor = MessageExecutors.instance().get();
        return new MessageConsumerRegistry(redisTemplate, objectMapper, messageSubscribeWare, executor);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public DelayMessageScanner messageDelayingScanner() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        Executor executor = MessageExecutors.instance().get();
        return new DelayMessageScanner(redisTemplate, objectMapper, executor);
    }

    @Bean
    public MessagePublisher messagePublisher() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        return new MessagePublisher(redisTemplate, objectMapper);
    }

}
