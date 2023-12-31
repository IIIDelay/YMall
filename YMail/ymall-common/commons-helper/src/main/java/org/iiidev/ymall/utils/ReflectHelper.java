/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import cn.hutool.core.lang.Assert;
import com.alibaba.excel.annotation.ExcelProperty;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.iiidev.ymall.execption.ServiceRuntimeException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ReflectHelper
 *
 * @Author IIIDelay
 * @Date 2023/7/25 20:57
 **/
@Slf4j
public class ReflectHelper {
    /**
     * getAllField
     *
     * @param inClass     inClass
     * @param ignoreNames ignoreNames
     * @return List<Field>
     */
    public <IN> List<Field> getAllField(Class<IN> inClass, Set<String> ignoreNames) {
        return FieldUtils.getAllFieldsList(inClass).stream().filter(field -> !ignoreNames.contains(field)).collect(Collectors.toList());
    }

    /**
     * getFieldMap
     *
     * @param inClass     inClass
     * @param ignoreNames ignoreNames
     * @return Map<String, Field>
     */
    public <IN> Map<String, Field> getFieldMap(Class<IN> inClass, Set<String> ignoreNames) {
        return getAllField(inClass, Collections.emptySet())
            .stream().collect(Collectors.toMap(Field::getName, Function.identity()));
    }

    /**
     * 获取属性的读写方法
     *
     * @param inClass inClass
     */
    public <IN> void getReadWriteMethod(Class<IN> inClass) {
        try {
            Assert.notNull(inClass, () -> ServiceRuntimeException.of("input param not empty"));
            BeanInfo beanInfo = Introspector.getBeanInfo(inClass);
            List<Pair<Method, Method>> readWriteMethod = new ArrayList<>();
            Lists.newArrayList(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor ->
                    readWriteMethod.add(Pair.of(propertyDescriptor.getReadMethod(), propertyDescriptor.getWriteMethod())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * modifyAnnotationAttr
     *
     * @param clazz     clazz
     * @param consumer  consumer
     * @param fieldName fieldName
     */
    public void modifyAnnotationAttr(Class clazz, Consumer<Map<String, Object>> consumer, String fieldName) {
        try {
            // 获取需要修改的属性
            Field field = clazz.getDeclaredField(fieldName);
            // 获取注解
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            // 生成代理类对象
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
            Field annotationValues = invocationHandler.getClass().getDeclaredField("memberValues");

            annotationValues.setAccessible(true);
            Map<String, Object> map = (Map<String, Object>) annotationValues.get(invocationHandler);
            consumer.accept(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
