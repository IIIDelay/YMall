/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.ymall.common.configuration.MybatisPlusConfig;
import org.ymall.commons.context.ApplicationBeanContext;

/**
 * BaseCategoryTrademarkControllerTest
 *
 * @author IIIDelay
 * @createTime 2023年03月08日 16:05:00
 */
@SpringBootTest
public class BaseCategoryTrademarkControllerTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testCache() {
        redisTemplate.opsForValue().set("lol","无极剑圣");
        Assertions.assertEquals(redisTemplate.opsForValue().get("lol"), "无极剑圣");
    }

    @Test
    public void testBean() {
        MybatisPlusConfig bean = ApplicationBeanContext.getBean(MybatisPlusConfig.class);
        System.out.println("bean = " + bean);
    }
}