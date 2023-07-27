/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.validator.errors;

/**
 * @author hkp
 * @date 2022/6/5 10:34 PM
 * @since 1.0
 */
public interface ExcelValidFieldError extends ExcelValidObjectError {


    /**
     * 获取列，从 1 开始
     *
     * @return
     */
    Integer getColumn();
}
