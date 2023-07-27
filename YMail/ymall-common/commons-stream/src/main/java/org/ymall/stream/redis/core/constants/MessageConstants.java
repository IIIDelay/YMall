/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis.core.constants;

import java.time.format.DateTimeFormatter;


public class MessageConstants {

    public static final String MSG_ID = "MQ_MSG_ID";
    public static final String MSG_TOPIC = "MQ_MSG_TOPIC";
    public static final String MSG_GROUP = "MQ_MSG_GROUP";
    public static final String MSG_BODY = "MQ_MSG_BODY";
    public static final String MSG_TIME = "MQ_MSG_TIME";
    public static final String MSG_DELAY_TIME = "MQ_MSG_DELAY_TIME";

    public static final String DELAY_QUEUE_KEY = "redis.delay.queue";
    public static final DateTimeFormatter MSG_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

}
