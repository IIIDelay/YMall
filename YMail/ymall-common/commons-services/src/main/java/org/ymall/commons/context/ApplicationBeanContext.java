/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationBeanContext
 *
 * @author IIIDelay
 * @createTime 2023年04月24日 21:26:00
 */
public class ApplicationBeanContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * getBean
     *
     * @param outClass outClass
     * @return OUT
     */
    public static <OUT> OUT getBean(Class<OUT> outClass) {
        return applicationContext.getBean(outClass);
    }
}
