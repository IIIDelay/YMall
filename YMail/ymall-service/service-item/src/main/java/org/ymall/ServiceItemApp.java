/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ServiceItemApp, 不进行DB操作
 *
 * @author IIIDelay
 * @createTime 2023年03月12日 20:13:00
 */
@SpringBootApplication(exclude = {MybatisPlusAutoConfiguration.class})
public class ServiceItemApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceItemApp.class, args);
    }
}
