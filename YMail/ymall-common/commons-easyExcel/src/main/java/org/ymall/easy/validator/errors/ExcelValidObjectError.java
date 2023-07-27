/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.validator.errors;

public interface ExcelValidObjectError {

    /**
     * 获取行号，从 1 开始
     *
     * @return
     */
    Integer getRow();

    /**
     * 获取错误消息
     *
     * @return
     */
    String getMessage();
}
