/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.common;

import lombok.AllArgsConstructor;

/**
 * Redis常量配置类
 * set name admin
 */
public class RedisConstants {

    public static final String SKUKEY_PREFIX = "sku:";
    public static final String SKUKEY_SUFFIX = ":info";
    // 单位：秒
    public static final long SKUKEY_TIMEOUT = 24 * 60 * 60;

    /**
     * 商品如果在数据库中不存在那么会缓存一个空对象进去，但是这个对象是没有用的，所以这个对象的过期时间应该不能太长，
     * 如果太长会占用内存。
     * 定义变量，记录空对象的缓存过期时间
     */
    public static final long SKUKEY_TEMPORARY_TIMEOUT = 10 * 60;

    // 单位：秒 尝试获取锁的最大等待时间
    public static final long SKULOCK_EXPIRE_PX1 = 1;
    // 单位：秒 锁的持有时间
    public static final long SKULOCK_EXPIRE_PX2 = 1;
    public static final String SKULOCK_SUFFIX = ":lock";

    public static final String USER_KEY_PREFIX = "user:";
    public static final String USER_CART_KEY_SUFFIX = ":cart";
    public static final long USER_CART_EXPIRE = 60 * 60 * 24 * 30;

    // 用户登录
    public static final String USER_LOGIN_KEY_PREFIX = "user:login:";
    //    public static final String userinfoKey_suffix = ":info";
    public static final int USERKEY_TIMEOUT = 60 * 60 * 24 * 7;

    // 秒杀商品前缀
    public static final String SECKILL_GOODS = "seckill:goods";
    public static final String SECKILL_ORDERS = "seckill:orders";
    public static final String SECKILL_ORDERS_USERS = "seckill:orders:users";
    public static final String SECKILL_STOCK_PREFIX = "seckill:stock:";
    public static final String SECKILL_USER = "seckill:user:";
    // 用户锁定时间 单位：秒
    public static final int SECKILL__TIMEOUT = 60 * 60 * 1;

    //  布隆过滤器使用！
    public static final String SKU_BLOOM_FILTER="sku:bloom:filter";
    public static final String REDIS_STEAM_01 = "";


    @AllArgsConstructor
    public enum DeploymentMode {
        STAND_ALONE("stand_alone"), CLUSTER("cluster"), SENTINEL("sentinel");
        public String name;
    }
}
