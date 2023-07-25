/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.lang;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**

 **/
public interface IQueue<E> {

    /**
     * 向队列中添加单个元素
     *
     * @param key Key键
     * @param e   元素
     * @return 添加成功返回 <code>true</code>
     */
    boolean push(String key, E e);

    /**
     * 批量向队列中添加元素
     *
     * @param key    Key键
     * @param values 元素集合
     * @return
     */
    boolean pushAll(String key, Collection<E> values);

    /**
     * 从队列中取出队首元素
     *
     * @param key Key键
     * @return 元素
     */
    E pop(String key);

    /**
     * 从队列中取出<code>count</code>个队首元素(最多)
     *
     * @return 元素
     */
    List<E> pop(String key, long count);

    /**
     * 阻塞从队列中取出队首元素
     *
     * @return 元素
     */
    E bPop(String key, long timeout, TimeUnit unit);

    /**
     * 清空队列
     *
     * @param key Key键
     */
    void clear(String key);

    /**
     * 获取队列中实际元素的个数
     *
     * @param key Key键
     * @return 元素个数
     */
    int size(String key);
}
