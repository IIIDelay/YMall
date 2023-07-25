/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.util;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;

public class TracerUtils {

    /**
     * 私有化构造方法
     */
    private TracerUtils() {
    }

    /**
     * 获得链路追踪编号，直接返回 SkyWalking 的 TraceId。
     * 如果不存在的话为空字符串！！！
     *
     * @return 链路追踪编号
     */
    public static String getTraceId() {
        return TraceContext.traceId();
    }

}