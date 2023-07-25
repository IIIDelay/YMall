

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.model;

/**
 * 用户存储K、V结构的Model实体类
 *
 * @author 赛先生和泰先生
 * @author 笔者专题技术博客 —— http://www.altitude.xin
 * @author B站视频 —— https://space.bilibili.com/1936685014
 **/
public class KVModel<K, V> {
    private K key;
    private V value;

    public KVModel() {
    }

    public KVModel(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public KVModel(KVModel<K, V> kvModel) {
        this.key = kvModel.key;
        this.value = kvModel.value;
    }


    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KVModel{" +
            "key=" + key +
            ", value=" + value +
            '}';
    }
}
