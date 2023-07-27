/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis.core.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageExecutors {

    private final Executor executor;

    public static MessageExecutors instance() {
        return MessageQueueExecutorsHolder.instance;
    }

    private MessageExecutors() {
        AtomicInteger index = new AtomicInteger(1);
        int processors = Runtime.getRuntime().availableProcessors();

        executor = Executors.newFixedThreadPool(processors,
                runnable -> {
                    Thread thread = new Thread(runnable);
                    thread.setName("redis-mq-" + index.getAndIncrement());
                    thread.setDaemon(true);
                    return thread;
                });
    }

    public Executor get() {
        return executor;
    }

    private static class MessageQueueExecutorsHolder {

        private static final MessageExecutors instance = new MessageExecutors();

    }

}
