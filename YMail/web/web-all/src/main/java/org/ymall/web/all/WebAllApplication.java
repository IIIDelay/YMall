/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.web.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)// 取消数据源自动配置
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"org.ymall"})
public class WebAllApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAllApplication.class, args);
    }
}
