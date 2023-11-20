/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * StackTraceUtils
 *
 * @Author IIIDelay
 * @Date 2023/11/20 21:35
 **/
public class StackTraceUtils {
    public static String getParentClassStackTrace(Class<?> currClass) {
        return getParentClassStackTrace(currClass, 3);
    }

    public static String getParentClassStackTrace(Class<?> currClass, int levelDeep) {
        StackTraceElement[] stackTrace = Thread.getAllStackTraces().get(Thread.currentThread());

        String tag;
        int parent = 0;
        for (int index = 1; index < stackTrace.length; index++) {
            StackTraceElement e = stackTrace[index];
            if (e != null && Objects.equals(e.getClassName(), currClass.getName())) {
                parent = index;
                break;
            }
        }
        StackTraceElement log = stackTrace[parent + 1];

        if (levelDeep <= 3) {
            levelDeep = 3;
        }

        String[] buildInfoArr = new String[levelDeep];
        for (int i = levelDeep; i >= 1; i--) {
            StackTraceElement parentStackTraceEle = stackTrace[parent + i];
            String stackInfo = fillStackInfo(parentStackTraceEle.getClassName(), parentStackTraceEle.getMethodName(), parentStackTraceEle.getLineNumber());
            buildInfoArr[i - 1] = stackInfo;
        }

        tag = StringUtils.join(ArrayUtil.reverse(buildInfoArr));
        if (StringUtils.isBlank(tag)) {
            tag = fillStackInfo(log.getClassName(), log.getMethodName(), log.getLineNumber());
        }
        return tag;
    }

    private static String fillStackInfo(String clazzName, String methodName, int row) {
        return StringUtils.join("==>|Clazz_Method: ", clazzName, "#", methodName, " <row: ", row, ">|");
    }
}