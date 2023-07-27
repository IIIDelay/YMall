/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis.core.ware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.MethodIntrospector.MetadataLookup;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.StringUtils;
import org.ymall.stream.redis.core.anno.MessageSubscribeDefinition;
import org.ymall.stream.redis.core.anno.Subscribe;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MessageSubscribeWare implements ApplicationContextAware, InitializingBean, DisposableBean {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Map<String, MessageSubscribeDefinition> subscribeDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() {
        init(applicationContext);
    }

    private void init(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            return;
        }
        log.info("Initialize message queue subscribe definition ...");
        // init redis message listener
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);

            Map<Method, Subscribe> subscribeMethods = null;   // referred to ：org.springframework.context.event
            // .EventListenerMethodProcessor.processBean
            try {
                subscribeMethods = MethodIntrospector.selectMethods(bean.getClass(), subscribeMetadataLookup());
            } catch (Throwable ex) {
                log.error("Initialize message subscribe definition method-handler resolve error for bean[" + beanDefinitionName + "].", ex);
            }

            if (subscribeMethods == null || subscribeMethods.isEmpty()) {
                continue;
            }

            for (Map.Entry<Method, Subscribe> methodEntry : subscribeMethods.entrySet()) {
                Method listenMethod = methodEntry.getKey();
                Subscribe subscribe = methodEntry.getValue();

                MessageSubscribeDefinition definition = new MessageSubscribeDefinition()
                    .setTopic(subscribe.topic());
                definition.setGroup(groupOf(subscribe));
                definition.setBean(bean);
                definition.setMethod(listenMethod);

                subscribeDefinitionMap.put(beanDefinitionName, definition);
            }
        }
        log.info("Initialize message queue subscribe definition completed.");
    }

    private String groupOf(Subscribe subscribe) {
        if (!StringUtils.isEmpty(subscribe.group())) {
            return subscribe.group();
        }
        return subscribe.topic() + "_default";
    }


    private MetadataLookup<Subscribe> subscribeMetadataLookup() {
        return method -> AnnotatedElementUtils.findMergedAnnotation(method, Subscribe.class);
    }

    public Map<String, MessageSubscribeDefinition> getAll() {

        return subscribeDefinitionMap;
    }

    public MessageSubscribeDefinition get(String topic) {

        return subscribeDefinitionMap.get(topic);
    }

    @Override
    public void destroy() throws Exception {
        log.info("Destroy message queue subscribe definition ...");
        if (subscribeDefinitionMap.size() > 0) {
            subscribeDefinitionMap.clear();
        }
        log.info("Destroy message queue subscribe definition completed.");
    }

}
