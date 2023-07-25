/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.list;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServiceListApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceListApplication.class, args);
    }
}
