/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.tools.yamll.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 集成 Redis 做消息队列
 */
@SpringBootApplication
public class SpringBootMqRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMqRedisApplication.class, args);
    }

}
