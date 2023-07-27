/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.validator.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hkp
 * @date 2022/6/6 10:59 AM
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultExcelObjectError implements ExcelValidObjectError {

    /**
     * 行号
     */
    private Integer row;

    /**
     * 错误消息
     */
    private String message;

}
