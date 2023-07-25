

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.entity;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * 锁元数据
 *

 **/
public class LockMeta {
    /**
     * 分布式锁实例
     */
    private RLock lock;
    /**
     * 排队等待加锁时间
     */
    private long waitTime;
    /**
     * 持有锁时间
     */
    private long leaseTime;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit;

    public LockMeta() {
    }

    public LockMeta(RLock lock, long waitTime, long leaseTime, TimeUnit timeUnit) {
        this.lock = lock;
        this.waitTime = waitTime;
        this.leaseTime = leaseTime;
        this.timeUnit = timeUnit;
    }

    public static LockMeta of(RLock lock, long waitTime, long leaseTime, TimeUnit timeUnit) {
        return new LockMeta(lock, waitTime, leaseTime, timeUnit);
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public long getLeaseTime() {
        return leaseTime;
    }

    public void setLeaseTime(long leaseTime) {
        this.leaseTime = leaseTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public RLock getLock() {
        return lock;
    }

    public void setLock(RLock lock) {
        this.lock = lock;
    }

}
