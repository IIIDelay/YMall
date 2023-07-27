/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.validator.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultExcelValidFieldError extends DefaultExcelObjectError implements ExcelValidFieldError {

    private Integer column;

    public DefaultExcelValidFieldError(Integer row, Integer column, String message) {
        super(row, message);
        this.column = column;
    }
}
