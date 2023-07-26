/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.factory;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.ymall.constant.ChainEnum;
import org.ymall.service.GatewayHandler;

/**
 * ChainFactory
 *
 * @Author IIIDelay
 * @Date 2023/7/13 21:49
 **/
@Component
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class ChainFactory implements InitializingBean {
    private final ApplicationContext applicationContext;

    private volatile GatewayHandler firstGateHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        createFirstChainHandler(ChainEnum.BLACK_LIST_CHAIN.currentNode);
    }

    public GatewayHandler createFirstChainHandler(String fistId) {
        if (firstGateHandler != null) {
            synchronized (ChainFactory.class) {
                if (firstGateHandler != null) {
                    return firstGateHandler;
                }
            }
        }

        ChainEnum chainEnum = ChainEnum.getNextChain(fistId);
        if (chainEnum == null) {
            return null;
        }

        GatewayHandler gatewayHandler = applicationContext.getBean(chainEnum.currentNode, GatewayHandler.class);
        String nextChainNode = chainEnum.nextNode;

        GatewayHandler tempGatewayHandler = gatewayHandler;

        while (StringUtils.isNotEmpty(nextChainNode)) {
            GatewayHandler contextBean = applicationContext.getBean(nextChainNode, GatewayHandler.class);
            if (contextBean == null) {
                continue;
            }
            nextChainNode = ChainEnum.getNextChain(nextChainNode).nextNode;
            tempGatewayHandler.setNextGatewayHandler(contextBean);
            if (StringUtils.isEmpty(nextChainNode)) {
                continue;
            }

            tempGatewayHandler = contextBean;
        }
        firstGateHandler = gatewayHandler;
        return firstGateHandler;
    }
}
