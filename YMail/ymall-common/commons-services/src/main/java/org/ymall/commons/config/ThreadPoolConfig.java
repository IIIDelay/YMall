/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.ymall.commons.config.properties.ToolProperties;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;

@EnableConfigurationProperties({ToolProperties.class})
@Configuration
public class ThreadPoolConfig {

    /**
     * 默认CPU密集型
     */
    @Bean(value = "cpuDenseExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor cpuDense(ToolProperties prop) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int logicCpus = Runtime.getRuntime().availableProcessors();
        if (prop.getPoolCpuNumber() != null) {
            executor.setCorePoolSize((int) (prop.getPoolCpuNumber() * 1.5));
        } else {
            executor.setCorePoolSize((int) (logicCpus * 1.5));
        }
        if (prop.getPoolCpuNumber() != null) {
            executor.setMaxPoolSize(prop.getPoolCpuNumber() * 3);
        } else {
            executor.setMaxPoolSize(logicCpus * 3);
        }
        // Spring默认使用LinkedBlockingQueue
        executor.setQueueCapacity(150);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix(prop.getPoolName());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 默认io密集型
     */
    @Bean(value = "ioDenseExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor ioDense(ToolProperties prop) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int logicCpus = Runtime.getRuntime().availableProcessors();
        if (prop.getPoolCpuNumber() != null) {
            executor.setCorePoolSize(prop.getPoolCpuNumber() * 80);
        } else {
            executor.setCorePoolSize(logicCpus * 80);
        }
        if (prop.getPoolCpuNumber() != null) {
            executor.setMaxPoolSize(prop.getPoolCpuNumber() * 200);
        } else {
            executor.setMaxPoolSize(logicCpus * 200);
        }
        // Spring默认使用LinkedBlockingQueue
        executor.setQueueCapacity(5);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix(prop.getPoolName());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }

    @Bean("cpuForkJoinPool")
    public ForkJoinPool cpuForkJoinPool() {
        int logicCpus = Runtime.getRuntime().availableProcessors();
        return new ForkJoinPool(logicCpus);
    }

    @Bean("ioForkJoinPool")
    public ForkJoinPool ioForkJoinPool() {
        int logicCpus = Runtime.getRuntime().availableProcessors();
        return new ForkJoinPool(logicCpus * 80);
    }
}
