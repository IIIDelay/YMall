/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Function;


import org.aspectj.lang.JoinPoint;

/**
 * 日志工具类
 */
public class LogUtil {

    /***
     * 获取操作信息
     * @param point
     * @return
     */
    public static <IN extends Annotation> String getControllerMethodDescription(JoinPoint point, Class<IN> inAnnoClazz, Function<IN, String> func) {
        try {
            // 获取连接点目标类名
            String targetName = point.getTarget().getClass().getName();
            // 获取连接点签名的方法名
            String methodName = point.getSignature().getName();
            // 获取连接点参数
            Object[] args = point.getArgs();
            // 根据连接点类的名字获取指定类
            Class targetClass = Class.forName(targetName);
            // 获取类里面的方法
            Method[] methods = targetClass.getMethods();
            String description = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == args.length) {
                        description = func.apply(method.getAnnotation(inAnnoClazz));
                        // description = method.getAnnotation(inAnnoClazz).value();
                        break;
                    }
                }
            }
            return description;
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 获取堆栈信息
     *
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
