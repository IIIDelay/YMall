package org.ymall.commons.cms.common.constant;

import org.ymall.commons.cms.common.util.EntityUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class RedisConstants {
    /**
     * 缓存前缀
     */
    public static String CACHE_PREFIX = "CACHE";
    /**
     * 锁前缀
     */
    public static String LOCK_PREFIX = "LOCK";
    /**
     * 消息前缀
     */
    public static String MSG_PREFIX = "MSG";
    /**
     * 队列前缀
     */
    public static String QUEUE_PREFIX = "QUEUE";

    private RedisConstants() {
    }

    /**
     * 创建缓存Key
     *
     * @param clazz 被缓存数据的Class对象
     * @param id    被缓存数据的主键ID
     * @param <T>   被缓存数据的类型
     * @return 字符串
     */
    public static <T,ID extends Serializable> String createCacheKey(Class<T> clazz, ID id) {
        return String.format("%s:%s:%s", CACHE_PREFIX, clazz.getName(), id);
    }

    /**
     * 创建缓存Key
     *
     * @param ids   被缓存数据的主键ID
     * @param <T>   被缓存数据的类型
     * @param clazz 被缓存数据的Class对象
     * @return 字符串
     */
    public static <T> List<String> createCacheKey(Class<T> clazz, Collection<? extends Serializable> ids) {
        return EntityUtils.toList(ids, e -> createCacheKey(clazz, e));
    }

    /**
     * 创建表锁Key
     *
     * @param className 被锁实体类名
     * @param <T>       被锁数据的类型
     * @return 字符串
     */
    public static <T> String createLockKey(String className) {
        return String.format("%s:%s", LOCK_PREFIX, className);
    }

    /**
     * 创建行锁Key
     *
     * @param clazz 被锁数据的Class对象
     * @param id    被锁数据的主键ID
     * @param <T>   被锁数据的类型
     * @return 字符串
     */
    public static <T> String createLockKey(Class<T> clazz, Serializable id) {
        return String.format("%s:%s:%s", LOCK_PREFIX, clazz.getName(), id);
    }

    /**
     * 创建队列Key
     *
     * @param clazz 被队列数据的Class对象
     * @param <T>   被队列数据的类型
     * @return 字符串
     */
    public static <T> String createQueueKey(Class<T> clazz) {
        return String.format("%s:%s", QUEUE_PREFIX, clazz.getName());
    }
}
