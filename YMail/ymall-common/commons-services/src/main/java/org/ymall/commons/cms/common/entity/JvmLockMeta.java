

package org.ymall.commons.cms.common.entity;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 锁元数据
 *

 **/
public class JvmLockMeta {
    /**
     * JVM锁实例
     */
    private Lock lock;
    /**
     * 排队等待加锁时间
     */
    private long waitTime;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit;

    public JvmLockMeta() {
    }

    public JvmLockMeta(Lock lock, long waitTime, TimeUnit timeUnit) {
        this.lock = lock;
        this.waitTime = waitTime;
        this.timeUnit = timeUnit;
    }

    public static JvmLockMeta of(Lock lock, long waitTime, TimeUnit timeUnit) {
        return new JvmLockMeta(lock, waitTime, timeUnit);
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

}
