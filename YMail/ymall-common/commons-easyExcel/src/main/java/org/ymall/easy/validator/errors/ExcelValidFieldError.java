/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.validator.errors;

public interface ExcelValidFieldError extends ExcelValidObjectError {


    /**
     * 获取列，从 1 开始
     *
     * @return
     */
    Integer getColumn();
}
