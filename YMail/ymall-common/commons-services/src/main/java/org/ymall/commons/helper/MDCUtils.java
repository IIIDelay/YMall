/*
 * Copyright (c) 2024. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;

/**
 * MDCUtils
 *
 * @Author IIIDelay
 * @Date 2023/12/20 21:53
 **/
public class MDCUtils {
    public static final String TRACE_ID_KEY = "traceId";

    public static String generateTraceIdAndFill(){
        String lowerCase = IdUtil.fastUUID().toLowerCase();
        MDC.put(TRACE_ID_KEY, lowerCase);
        return lowerCase;
    }

    public static String getTraceId(){
        return MDC.get(TRACE_ID_KEY);
    }

    public static void clearTrace(){
        MDC.remove(TRACE_ID_KEY);
    }
}