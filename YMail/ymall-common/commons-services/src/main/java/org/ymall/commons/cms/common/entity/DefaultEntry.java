

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.entity;

import java.util.Map;

/**
 * <p>{@link Map.Entry}的默认实现类 用于快速创建{@link Map}实例</p>
 *

 **/
public class DefaultEntry<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public DefaultEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return this.value;
    }
}
