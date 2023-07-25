/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ServiceWareApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceWareApplication.class, args);
	}

}
