/*
 * Copyright (c) 2024. 版权归III_Delay所有
 */

package org.ymall.commons.config;

import org.slf4j.TtlMDCAdapter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * BeanInit
 *
 * @Author IIIDelay
 * @Date 2024/2/2 22:37
 **/
@Component
public class BeanInit implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        TtlMDCAdapter.getInstance();
    }
}