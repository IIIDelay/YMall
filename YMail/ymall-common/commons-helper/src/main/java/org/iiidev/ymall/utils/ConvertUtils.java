/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import org.iiidev.ymall.func.MyFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 
 * @description 通过
 * @date 2023-06-30 09:47
 */
public class ConvertUtils {

    public ConvertUtils() {
    }

    public static final String GET = "get";

    public static final String IS = "is";

    private static final Map<Class<?>, SerializedLambda> CLASS_LAMBDA_CACHE = new ConcurrentHashMap<>();

    private static SerializedLambda getSerializedLambda(MyFunction<?, ?> fn) {
        SerializedLambda lambda = CLASS_LAMBDA_CACHE.get(fn.getClass());
        // 先检查缓存中是否存在
        if (lambda == null) {
            try {
                // 提取SerializedLambda并缓存
                Method method = fn.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                lambda = (SerializedLambda) method.invoke(fn);
                CLASS_LAMBDA_CACHE.put(fn.getClass(), lambda);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return lambda;
    }

    /**
     * @param fn lambda表达式
     * @return {@link String}
     * @date 2023-06-30 10:51:22     
     * @description 将lambda表达式转换为属性名
     */
    public static <T, R> String getLambdaFieldName(MyFunction<T, R> fn) {
        SerializedLambda lambda = getSerializedLambda(fn);
        String methodName = lambda.getImplMethodName();
        if (methodName.startsWith(GET)) {
            methodName = methodName.substring(3);
        } else if (methodName.startsWith(IS)) {
            methodName = methodName.substring(2);
        } else {
            throw new IllegalArgumentException("无效的getter方法：" + methodName);
        }
        return StringUtils.firstToLowerCase(methodName);
    }
}
