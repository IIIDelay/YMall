/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis.core.anno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSubscribeDefinition {

    private String topic;

    private String group;

    private Object bean;

    private Method method;

}
