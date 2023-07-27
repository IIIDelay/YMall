/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.validator;

import org.ymall.easy.validator.errors.ExcelValidErrors;

public interface ExcelValidator<T> {

    /**
     * 校验
     *
     * @param readRows 读取的行信息
     * @return
     */
    ExcelValidErrors validate(ReadRows<T> readRows);

}
