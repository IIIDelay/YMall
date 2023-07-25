/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.config;

import org.springframework.context.annotation.Bean;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TaskConfig
 *
 * @Author IIIDelay
 * @Date 2023/6/5 21:44
 **/
public class TaskConfig {
    @Bean
    public ThreadPoolExecutor productThreadPool() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        return executor;
    }
}
