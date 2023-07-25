/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ymall.constant.ChainEnum;
import org.ymall.factory.ChainFactory;
import org.ymall.service.GatewayHandler;

/**
 * ChainController
 *
 * @Author IIIDelay
 * @Date 2023/7/13 22:42
 **/
@RestController
@RequestMapping
public class ChainController {
    @Autowired
    private ChainFactory chainFactory;

    @RequestMapping("check")
    public void check() {
        GatewayHandler firstChainHandler = chainFactory.createFirstChainHandler(ChainEnum.BLACK_LIST_CHAIN.currentNode);
        firstChainHandler.valid();
    }

}
