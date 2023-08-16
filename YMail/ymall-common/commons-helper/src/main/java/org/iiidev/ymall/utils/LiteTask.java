/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import cn.hutool.core.lang.Assert;
import lombok.Setter;
import org.iiidev.ymall.common.ThreadPoolEnum;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * LiteTask
 *
 * @Author IIIDelay
 * @Date 2023/8/16 21:18
 **/
@Setter
public class LiteTask<IN> {
    private List<IN> inList;

    private ThreadPoolExecutor threadPoolExecutor;

    public static <IN> LiteTask<IN> node(List<IN> inList, ThreadPoolEnum poolEnum) {
        Assert.notEmpty(inList, () -> new RuntimeException("入参不能为空"));
        LiteTask<IN> liteTask = new LiteTask<>();
        liteTask.setInList(inList);
        liteTask.setThreadPoolExecutor(ThreadPoolEnum.getTPE(poolEnum));
        return liteTask;
    }

    public static <IN> LiteTask<IN> node(List<IN> inList) {
        return node(inList, ThreadPoolEnum.CPU);
    }
}
