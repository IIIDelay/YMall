/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import cn.hutool.core.lang.Assert;
import lombok.Setter;
import org.iiidev.ymall.common.ThreadPoolEnum;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * LiteTask
 *
 * @Author IIIDelay
 * @Date 2023/8/16 21:18
 **/
@Setter
public class LiteTask<IN> {
    private List<IN> inList;

    private ThreadPoolExecutor threadPoolExecutor;

    public static <IN> LiteTask<IN> node(List<IN> inList, ThreadPoolEnum poolEnum) {
        Assert.notEmpty(inList, () -> new RuntimeException("入参不能为空"));
        LiteTask<IN> liteTask = new LiteTask<>();
        liteTask.setInList(inList);
        liteTask.setThreadPoolExecutor(ThreadPoolEnum.getTPE(poolEnum));
        return liteTask;
    }

    public static <IN> LiteTask<IN> node(List<IN> inList) {
        return node(inList, ThreadPoolEnum.CPU);
    }

    /**
     * allJoinReturn 获取所有线程结果
     *
     * @param allFuture allFuture
     * @return List<OUT>
     */
    public static <OUT> List<OUT> allJoinReturn(List<CompletableFuture<OUT>> allFuture) {
        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(allFuture.toArray(new CompletableFuture[allFuture.size()]));
        CompletableFuture<List<OUT>> outListFuture = completableFuture.thenApply(future ->
            allFuture.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
        return outListFuture.join();
    }

    /**
     * 如果在给定超时之前未完成，则异常完成此 CompletableFuture 并抛出 {@link TimeoutException} 。
     * 参考: https://bugs.openjdk.org/browse/JDK-8132960 & https://juejin.cn/post/7197606993858281530
     *
     * @param timeout 在出现 TimeoutException 异常完成之前等待多长时间，以 {@code unit} 为单位
     * @param unit    一个 {@link TimeUnit}，结合 {@code timeout} 参数，表示给定粒度单位的持续时间
     * @return 入参的 CompletableFuture
     */
    public static <T> CompletableFuture<T> orTimeout(CompletableFuture<T> future, long timeout, TimeUnit unit) {
        if (null == unit) {
            throw new RuntimeException("时间的给定粒度不能为空");
        }
        if (null == future) {
            throw new RuntimeException("异步任务不能为空");
        }
        if (future.isDone()) {
            return future;
        }

        return future.whenComplete(new Canceller(Delayer.delay(new Timeout(future), timeout, unit)));
    }

    /**
     * 超时时异常完成的操作
     */
    static final class Timeout implements Runnable {
        final CompletableFuture<?> future;

        Timeout(CompletableFuture<?> future) {
            this.future = future;
        }

        public void run() {
            if (null != future && !future.isDone()) {
                future.completeExceptionally(new TimeoutException());
            }
        }
    }

    /**
     * 取消不需要的超时的操作
     */
    static final class Canceller implements BiConsumer<Object, Throwable> {
        final Future<?> future;

        Canceller(Future<?> future) {
            this.future = future;
        }

        public void accept(Object ignore, Throwable ex) {
            if (null == ex && null != future && !future.isDone()) {
                future.cancel(false);
            }
        }
    }

    /**
     * 单例延迟调度器，仅用于启动和取消任务，一个线程就足够
     */
    static final class Delayer {
        static ScheduledFuture<?> delay(Runnable command, long delay, TimeUnit unit) {
            return delayer.schedule(command, delay, unit);
        }

        static final class DaemonThreadFactory implements ThreadFactory {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setName("CompletableFutureExpandUtilsDelayScheduler");
                return t;
            }
        }

        static final ScheduledThreadPoolExecutor delayer;

        static {
            delayer = new ScheduledThreadPoolExecutor(1, new DaemonThreadFactory());
            delayer.setRemoveOnCancelPolicy(true);
        }
    }
}
