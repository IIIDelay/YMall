/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ServiceCartApp
 *
 * @author IIIDelay
 * @createTime 2023年03月08日 17:46:00
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(basePackages = "org.ymall")
public class ServiceCartApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCartApp.class, args);
    }
}
