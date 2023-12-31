/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@ComponentScan({"org.ymall"})
@EnableDiscoveryClient
public class ServiceMqApplication {

   public static void main(String[] args) {
      SpringApplication.run(ServiceMqApplication.class, args);
   }
}