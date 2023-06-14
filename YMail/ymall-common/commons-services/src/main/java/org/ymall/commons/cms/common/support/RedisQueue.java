

package org.ymall.commons.cms.common.support;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.ymall.commons.cms.common.lang.IQueue;
import org.ymall.commons.cms.common.util.JacksonUtils;
import org.ymall.commons.cms.common.util.RedisUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <p>定义以Redis为底层实现的队列</p>
 * <p>将双端队列转化成普通队列：定义<i>左侧入队；右侧出队</i></p>
 *

 **/
public class RedisQueue<E> implements IQueue<E> {
    private final ListOperations<String, E> list;


    public RedisQueue(ListOperations<String, E> list) {
        this.list = list;
    }

    /**
     * 向队列中添加单个元素
     *
     * @param key Key键
     * @param e   元素
     * @return 添加成功返回 <code>true</code>
     */
    @Override
    public boolean push(String key, E e) {
        Long result = list.leftPush(key, e);
        return result != null && result > 0;
    }


    /**
     * 批量向队列中添加元素
     *
     * @param key    Key键
     * @param values 元素集合
     */
    @Override
    public boolean pushAll(String key, Collection<E> values) {
        Objects.requireNonNull(values);
        Long result = list.leftPushAll(key, values);
        return result != null && result > 0;
    }

    /**
     * 从队列中取出队首元素
     *
     * @param key Key键
     * @return 元素
     */
    @Override
    public E pop(String key) {
        return list.rightPop(key);
    }

    /**
     * 从队列中取出<code>count</code>个队首元素(最多)
     *
     * @return 元素
     */
    @Override
    public List<E> pop(String key, long count) {
        return list.rightPop(key, count);
    }

    /**
     * 阻塞从队列中取出队首元素
     *
     * @return 元素
     */
    @Override
    public E bPop(String key, long timeout, TimeUnit unit) {
        return list.rightPop(key, timeout, unit);
    }

    /**
     * 清空队列
     *
     * @param key Key键
     */
    @Override
    public void clear(String key) {
        RedisUtils.remove(key);
    }

    /**
     * 获取队列中实际元素的个数
     *
     * @param key Key键
     * @return 元素个数
     */
    @Override
    public int size(String key) {
        return Optional.ofNullable(list.size(key)).map(Long::intValue).orElse(0);
    }


    private <T> T getT(String key, Class<T> clazz, RedisConnection conn) {
        List<byte[]> bytes = conn.bRPop(30, key.getBytes(StandardCharsets.UTF_8));
        if (bytes != null && bytes.size() >= 2) {
            String valueString = new String(bytes.get(1), StandardCharsets.UTF_8);
            return JacksonUtils.readObjectValue(valueString, clazz);
        }
        return null;
    }
}
