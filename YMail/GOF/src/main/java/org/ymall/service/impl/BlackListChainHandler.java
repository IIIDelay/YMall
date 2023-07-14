package org.ymall.service.impl;

import org.springframework.stereotype.Service;
import org.ymall.service.GatewayHandler;

/**
 * BlackListChainHandler
 *
 * @Author IIIDelay
 * @Date 2023/7/13 21:54
 **/
@Service
public class BlackListChainHandler extends GatewayHandler {

    @Override
    public void valid() {
        System.out.println("黑名单校验...");
        // 子调用, 是因为每个子 GatewayHandler 是不同的
        next();
    }
}
