

package org.ymall.commons.cms.common.util;

import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ymall.commons.cms.common.entity.JvmLockMeta;
import org.ymall.commons.cms.common.entity.LockMeta;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

/**
 * 锁相关工具类
 *

 **/
public class LockUtils {
    private final static Logger logger = LoggerFactory.getLogger(LockUtils.class);

    private LockUtils() {
    }


    /**
     * 通过封装减少try-catch冗余代码
     *
     * @param meta     {@link LockMeta}锁元数据子类实例
     * @param supplier 回调方法
     * @param <R>      返回值类型
     */
    public static <R> R tryLock(LockMeta meta, Supplier<R> supplier) {
        Objects.requireNonNull(supplier);
        RLock lock = Objects.requireNonNull(meta.getLock());
        try {
            if (lock.tryLock(meta.getWaitTime(), meta.getLeaseTime(), meta.getTimeUnit())) {
                // 回调被锁业务逻辑
                return supplier.get();
            } else {
                long sec = TimeUnit.SECONDS.convert(meta.getWaitTime(), meta.getTimeUnit());
                logger.error("等待{}秒仍未获得锁，已超时，请重新申请锁", sec);
                throw new RuntimeException("获取锁等待超时");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Optional.of(lock).ifPresent(Lock::unlock);
        }
    }

    /**
     * 通过封装减少try-catch冗余代码
     *
     * @param meta     {@link LockMeta}锁元数据子类实例
     * @param supplier 回调方法
     * @param <R>      返回值类型
     */
    public static <R> R tryLock(JvmLockMeta meta, Supplier<R> supplier) {
        Objects.requireNonNull(supplier);
        Lock lock = Objects.requireNonNull(meta.getLock());
        try {
            if (lock.tryLock(meta.getWaitTime(), meta.getTimeUnit())) {
                // 回调被锁业务逻辑
                return supplier.get();
            } else {
                throw new RuntimeException("获取锁等待超时");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Optional.of(lock).ifPresent(Lock::unlock);
        }
    }
}
