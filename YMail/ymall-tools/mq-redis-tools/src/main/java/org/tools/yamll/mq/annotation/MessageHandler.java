/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.tools.yamll.mq.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 处理器注解，不同的类型使用不同的注解标准
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MessageHandler {
    MessageListener.Mode value() default MessageListener.Mode.TOPIC;
}
