/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.execption;

import lombok.Getter;

import java.util.List;

/**
 * 自定义参数异常
 */
@Getter
public class ParamException extends RuntimeException {

    private final List<String> fieldList;

    private final List<String> msgList;

    public ParamException(List<String> fieldList, List<String> msgList) {
        this.fieldList = fieldList;
        this.msgList = msgList;
    }
}