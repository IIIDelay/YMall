package org.ymall.service.impl;

import org.springframework.stereotype.Service;
import org.ymall.service.GatewayHandler;

/**
 * InterceptorChainHandler
 *
 * @Author IIIDelay
 * @Date 2023/7/13 22:05
 **/
@Service
public class InterceptorChainHandler extends GatewayHandler {
    @Override
    public void valid() {
        System.out.println("拦截器校验...");
        next();
    }
}
