/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.util;

import org.iiidev.ymall.common.StrPool;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象属性赋值工具类
 */
public class BeanCopyUtils {
    private static Map<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();

    /**
     * copyProperties
     *
     * @param source           source
     * @param target           target
     * @param ignoreProperties ignoreProperties
     * @return U
     */
    public static <T,U> U copyProperties(T source, U target, String ...ignoreProperties) {
        if (ArrayUtils.isNotEmpty(ignoreProperties)) {
            BeanUtils.copyProperties(source, target,ignoreProperties);
            return target;
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 初始化 BeanCopier
     *
     * @param source
     * @param target
     * @return
     */
    private static BeanCopier initCopier(Class source, Class target) {
        BeanCopier beanCopier = BeanCopier.create(source, target, false);
        beanCopierMap.put(source.getName() + "_" + target.getName(), beanCopier);
        return beanCopier;
    }

    /**
     * 获取BeanCopier
     *
     * @param source
     * @param target
     * @return
     */
    private static BeanCopier getBeanCopier(Class source, Class target) {
        String combineName = StringUtils.join(source.getClass().getName(), StrPool.UNDERSCORE, target.getName());
        BeanCopier beanCopier = beanCopierMap.get(combineName);
        if (beanCopier != null) {
            return beanCopier;
        }
        return initCopier(source, target);
    }

    /**
     * Pojo 类型转换（浅复制，字段名&类型相同则被复制）
     *
     * @param source      原对象
     * @param targetClass 目标类
     * @param <T>
     * @return
     */
    public static <T, U> U convert(T source, Class<U> targetClass) {
        if (source == null) {
            return null;
        }
        BeanCopier beanCopier = getBeanCopier(source.getClass(), targetClass);
        try {
            U target = targetClass.getDeclaredConstructor().newInstance();
            beanCopier.copy(source, target, null);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("对象拷贝失败" + source + "_" + targetClass);
        }
    }

    /**
     * Pojo 类型转换（浅复制，字段名&类型相同则被复制）
     *
     * @param source      原对象
     * @param targetClass 目标类
     * @param <E>
     * @return
     */
    public static <E> List<E> convert(List source, Class<E> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            if (source.isEmpty()) {
                return source.getClass().getDeclaredConstructor().newInstance();
            }
            List result = source.getClass().getDeclaredConstructor().newInstance();
            for (Object each : source) {
                result.add(convert(each, targetClass));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("对象拷贝失败" + source + "_" + targetClass);
        }
    }
}
