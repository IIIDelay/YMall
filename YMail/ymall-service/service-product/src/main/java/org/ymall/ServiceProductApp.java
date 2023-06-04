/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall;

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
public class ServiceProductApp {
    public static void main(String[] args) {
        try {
            SpringApplication.run(ServiceProductApp.class, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
