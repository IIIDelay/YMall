package org.ymall.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.ymall"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages= {"org.ymall"})
public class ServiceActivityApplication {

   public static void main(String[] args) {
      SpringApplication.run(ServiceActivityApplication.class, args);
   }

}