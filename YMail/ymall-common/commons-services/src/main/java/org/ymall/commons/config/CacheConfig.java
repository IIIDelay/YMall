/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * CacheConfig
 *
 * @Author IIIDelay
 * @Date 2023/9/14 20:18
 **/
public class CacheConfig {
    @Bean
    public CacheManager RedisManger(RedisConnectionFactory factory){
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(factory)
            .cacheDefaults(configuration)
            .build();
        return redisCacheManager;
    }


}
