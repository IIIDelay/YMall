/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.service.impl;

import org.springframework.stereotype.Service;
import org.ymall.service.GatewayHandler;

/**
 * ParamChainHandler
 *
 * @Author IIIDelay
 * @Date 2023/7/13 22:06
 **/
@Service
public class ParamChainHandler extends GatewayHandler {
    @Override
    public void valid() {
        System.out.println("参数校验...");
        next();
    }
}
