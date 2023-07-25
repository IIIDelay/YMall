/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.pojo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

/**
 * StreamErrorHandler
 *
 * @Author IIIDelay
 * @Date 2023/6/15 22:55
 **/
@Slf4j
public class StreamErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        log.error("redis mq error", t);
    }
}
