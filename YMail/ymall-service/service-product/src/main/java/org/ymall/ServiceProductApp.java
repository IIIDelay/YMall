/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall;

import common.RedisConstants;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ServiceProductApp
 *
 * @author IIIDelay
 * @createTime 2023年02月28日 22:01:00
 */
@SpringBootApplication
// @EnableDiscoveryClient // nacos 2.x版本以后不需要此注解默认开启服务注册与发现
public class ServiceProductApp implements CommandLineRunner {
    @Autowired
    private RedissonClient redissonClient;

    public static void main(String[] args) {
        SpringApplication.run(ServiceProductApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RBloomFilter<Object> rbloomFilter = redissonClient.getBloomFilter(RedisConstants.SKU_BLOOM_FILTER);
        // 初始化布隆过滤器，预计统计元素数量为100000，期望误差率为0.01
        rbloomFilter.tryInit(100000, 0.01);
    }

}
