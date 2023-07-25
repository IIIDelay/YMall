/*
 * Copyright (c) 2020-2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.function;

/**
 * 无输入无输出回调方法
 * <p>
 * 参考{@link xin.altitude.cms.common.util.BooleanUtils#ifTrue(boolean, PlainCaller)}的使用
 *

 **/
@FunctionalInterface
public interface PlainCaller {
    /**
     * 执行回调
     */
    void handle();
}
