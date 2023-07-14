package org.ymall.service.impl;

import org.springframework.stereotype.Service;
import org.ymall.service.GatewayHandler;

/**
 * UserInfoChainHandler
 *
 * @Author IIIDelay
 * @Date 2023/7/13 22:06
 **/
@Service
public class UserInfoChainHandler extends GatewayHandler {
    @Override
    public void valid() {
        System.out.println("用户信息校验...");
        next();
    }
}
