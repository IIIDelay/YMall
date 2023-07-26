/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.valid.test.exception;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.ymall.easy.validator.ExcelValidException;
import org.ymall.easy.validator.errors.ExcelValidErrors;

@RestControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handleException(ExcelValidException e) {
        ExcelValidErrors errors = e.getErrors();
        return JSON.toJSONString(errors);
    }
}
