/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolEnum
 *
 * @Author IIIDelay
 * @Date 2023/8/16 21:33
 **/
@Component
public enum ThreadPoolEnum implements ApplicationContextAware {
    IO,
    CPU;

    private static ApplicationContext applicationContext;

    public static ThreadPoolExecutor getTPE(ThreadPoolEnum poolEnum) {
        if (IO.equals(poolEnum)) {
            return applicationContext.getBean("ioDenseExecutor", ThreadPoolExecutor.class);
        } else if (CPU.equals(poolEnum)) {
            return applicationContext.getBean("ioDenseExecutor", ThreadPoolExecutor.class);
        } else {
            throw new RuntimeException("无可用线程池");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
