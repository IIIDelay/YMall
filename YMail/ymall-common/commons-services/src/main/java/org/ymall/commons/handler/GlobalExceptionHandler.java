/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.handler;

import org.iiidev.ymall.execption.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.iiidev.ymall.result.Result;

/**
 * 全局异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Void> error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * 自定义异常处理方法
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceRuntimeException.class)
    @ResponseBody
    public Result error(ServiceRuntimeException e) {
        return Result.fail(e.getMessage());
    }
}
