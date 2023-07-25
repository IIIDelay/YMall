/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.constant;

import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * ChainEnum
 *
 * @Author IIIDelay
 * @Date 2023/7/13 22:14
 **/
@AllArgsConstructor
public enum ChainEnum {
    BLACK_LIST_CHAIN("blackListChainHandler", null, "interceptorChainHandler"),
    INTERCEPTOR_CHAIN("interceptorChainHandler", "blackListChainHandler", "userInfoChainHandler"),
    USER_INFO_CHAIN("userInfoChainHandler", "interceptorChainHandler", "paramChainHandler"),
    PARAM_CHAIN("paramChainHandler", "userInfoChainHandler", null);

    public final String currentNode;

    public final String preNode;

    public final String nextNode;

    public static ChainEnum getNextChain(String node) {
        return Arrays.stream(values())
            .filter(chainEnum -> chainEnum.currentNode.equals(node))
            .findFirst()
            .orElse(null);
    }
}
