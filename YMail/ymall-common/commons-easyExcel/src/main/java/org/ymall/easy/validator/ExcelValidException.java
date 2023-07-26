package org.ymall.easy.validator;

import org.ymall.easy.validator.errors.ExcelValidErrors;

public class ExcelValidException extends RuntimeException {

    private ExcelValidErrors errors;

    public ExcelValidException(String message, ExcelValidErrors errors) {
        super(message);
        this.errors = errors;
    }

    public ExcelValidErrors getErrors() {
        return errors;
    }

}
