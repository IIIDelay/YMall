/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iiidev.ymall.common.RedisConstants;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.ymall.commons.config.manger.CommonConfigManger;

import java.time.Duration;

/**
 * Redis配置类
 */
@EnableCaching
public class RedisConfig {

    /**
     * redisTemplate
     *
     * @param redisConnectionFactory redisConnectionFactory
     * @return RedisTemplate<Object, Object>
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        //  设置redis的连接池工厂。
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //  设置序列化的。
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //  将Redis 中 string ，hash 数据类型，自动序列化！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //  设置数据类型是Hash 的 序列化！
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * redisConnectionFactory
     *
     * @return RedisConnectionFactory
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(CommonConfigManger cm) {
        // 单机版redis配置
        RedisConfiguration redisConfiguration =
                cm.getCommonConfigProperties().redisConfiguration(RedisConstants.DeploymentMode.STAND_ALONE);

        // redis连接池配置
        GenericObjectPoolConfig pool = new GenericObjectPoolConfig();
        pool.setMaxIdle(10);
        pool.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(10).getSeconds());
        pool.setMinIdle(10);

        // LettucePoolingClientConfiguration配置
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder clientConfigurationBuild =
                LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofSeconds(120));
        clientConfigurationBuild.shutdownTimeout(Duration.ofSeconds(3));
        clientConfigurationBuild.poolConfig(pool);
        LettucePoolingClientConfiguration poolingClientConfiguration = clientConfigurationBuild.build();

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfiguration,
                poolingClientConfiguration);
        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }
}
