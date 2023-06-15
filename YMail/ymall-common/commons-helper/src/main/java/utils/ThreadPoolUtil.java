package utils;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public class ThreadPoolUtil {

    public static final long DEFAULT_WAIT_SECONDS = 5000;

    private static class ThreadPool {
        private static final ThreadFactory namedThreadFactory =
            new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();
        private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(20, 40, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(200), namedThreadFactory, new ThreadPoolExecutor.DiscardPolicy()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                printException(r, t);
            }
        };
    }

    /**
     * 获取线程池.
     */
    public static ExecutorService getThreadPool() {
        log.info("ThreadPool task num now: {}", ThreadPool.pool.getActiveCount());
        return ThreadPool.pool;
    }

    private static void printException(Runnable r, Throwable t) {
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null) {
            log.error(t.getMessage(), t);
        }
    }

    /**
     * 使用线程池并发执行任务.
     *
     * @param callables   任务列表
     * @param waitSeconds 等待时间（秒）
     * @return.
     */
    public static <T> List<T> executeTasks(List<Callable<T>> callables, long waitSeconds) {
        return doExecute(callables, waitSeconds, ThreadPool.pool);
    }

    private static <T> List<T> doExecute(List<Callable<T>> callables, long waitSeconds, ThreadPoolExecutor executor) {
        try {
            List<T> result = Lists.newArrayList();
            if (callables == null || callables.isEmpty()) {
                return result;
            }
            List<Future<T>> futures = executor.invokeAll(callables);
            try {
                for (Future<T> future : futures) {
                    result.add(future.get(waitSeconds, TimeUnit.SECONDS));
                }
            } catch (Exception e) {
                throw e;
            } finally {
                for (Future<T> future : futures) {
                    try {
                        future.cancel(true);
                    } catch (Exception e2) {
                    }
                }
            }
            return result;
        } catch (Exception e) {
            log.error("doExecute error", e);
            throw new RuntimeException(e.getMessage());
        }
    }


    public String getTableOwnersByTableGuid1(List<String> tableGuids, Supplier<String> supplier) {
        String ret = "[";
        List<CompletableFuture<String>> completableFutureList = Lists.newArrayList();
        tableGuids.stream().forEach(tableGuid -> {
            try {
                CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier);
                completableFutureList.add(future);
            } catch (Exception e) {
                log.info("getTableOwnersByWorkOrderId queryOdpsTableBaseInfo fail");
            }
        });
        List<String> ownerIdList = completableFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        for (String ownerId : ownerIdList) {
            ret += "'" + ownerId + "',";
        }
        return StringUtils.stripEnd(ret, ",") + "]";


    }
}
