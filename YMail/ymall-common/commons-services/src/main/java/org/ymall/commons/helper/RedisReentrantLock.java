/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisReentrantLock implements ApplicationContextAware {
    private static RedisReentrantLock redisReentrantLock;

    public static void run(String lockKey, String clientId, Duration duration, Runnable runnable) {
        if (redisReentrantLock.doTryAcquireLock(lockKey, clientId, duration.toMillis())) {
            try {
                runnable.run();
                return;
            } finally {
                redisReentrantLock.doReleaseLock(lockKey, clientId);
            }
        }
        log.warn("doTryAcquireLock failed");
    }

    public static void runWatchDog(String lockKey, String clientId, Duration expireTime, Runnable runnable) {
        if (redisReentrantLock.doTryAcquireLock(lockKey, clientId, expireTime.toMillis())) {
            boolean shutdown = false;
            try {
                Executors.newScheduledThreadPool(1)
                    .schedule(() -> {
                        // 延长锁的过期时间
                        boolean lock = redisReentrantLock.doExtendLock(lockKey, clientId, expireTime.toMillis());
                        if (!lock || shutdown) {
                            // 锁已被释放或重入次数已归零，跳出循环
                            return;
                        }
                    }, 5, TimeUnit.MILLISECONDS);

                // 真正业务执行
                runnable.run();
                return;
            } finally {
                shutdown = true;
                redisReentrantLock.doReleaseLock(lockKey, clientId);
            }
        }
        log.warn("doTryAcquireLock failed");
    }

    private RedisTemplate<String, String> redisTemplate;
    private RedisScript<Boolean> lockScript;
    private RedisScript<Boolean> unlockScript;
    private RedisScript<Boolean> extendScript;
    private RedisScript<Long> watchDogScript;

    public RedisReentrantLock(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.lockScript = new DefaultRedisScript<>("if redis.call('exists', KEYS[1]) == 0 then redis.call('hset', KEYS[1], ARGV[1], 1) redis.call('expire', KEYS[1], ARGV[2]) return true end if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then redis.call('hincrby', KEYS[1], ARGV[1], 1) redis.call('expire', KEYS[1], ARGV[2]) return true end return false", Boolean.class);
        this.unlockScript = new DefaultRedisScript<>("if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then redis.call('hincrby', KEYS[1], ARGV[1], -1) if redis.call('hget', KEYS[1], ARGV[1]) == '0' then redis.call('del', KEYS[1]) return true end return true end return false", Boolean.class);
        this.extendScript = new DefaultRedisScript<>("if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then redis.call('expire', KEYS[1], ARGV[2]) return true end return false", Boolean.class);
        this.watchDogScript = new DefaultRedisScript<>("if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then redis.call('expire', KEYS[1], ARGV[2]) return true end return false", Long.class);
    }

    private boolean doTryAcquireLock(String lockKey, String clientId, long expireTime) {
        Boolean execute = redisTemplate.execute(lockScript, Collections.singletonList(lockKey), clientId, String.valueOf(expireTime));
        return execute != null && execute;
    }

    private boolean doReleaseLock(String lockKey, String clientId) {
        Boolean execute = redisTemplate.execute(unlockScript, Collections.singletonList(lockKey), clientId);
        return execute != null && execute;
    }

    private boolean doExtendLock(String lockKey, String clientId, long expireTime) {
        Boolean execute = redisTemplate.execute(extendScript, Collections.singletonList(lockKey), clientId, String.valueOf(expireTime));
        return execute != null && execute;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        redisReentrantLock = applicationContext.getBean(RedisReentrantLock.class);
    }
}