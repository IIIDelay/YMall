package org.ymall.easy.validator.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hkp
 * @date 2022/6/6 11:00 AM
 * @since
 */
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
