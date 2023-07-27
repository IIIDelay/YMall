/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis.core.listener;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.ymall.stream.redis.core.ConsumeStatus;

@FunctionalInterface
public interface MessageListener {
    ConsumeStatus consume(MapRecord<String, String, String> message);
}
