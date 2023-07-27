/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;


public class MessageErrorHandler implements ErrorHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleError(Throwable throwable) {

        log.error(throwable.getMessage());
    }

}
