/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.validator;

import org.ymall.easy.validator.errors.ExcelValidErrors;

import java.util.ArrayList;
import java.util.List;

public class CompositeExcelValidator implements ExcelValidator<Object> {

    private List<ExcelValidator<Object>> validators;

    public CompositeExcelValidator(List<ExcelValidator<Object>> validators) {
        this.validators = new ArrayList<>(validators);
    }

    public boolean addValidator(ExcelValidator<Object> validator) {
        return this.validators.add(validator);
    }

    @Override
    public ExcelValidErrors validate(ReadRows<Object> readRows) {
        ExcelValidErrors errors = new ExcelValidErrors();
        for (ExcelValidator<Object> validator : this.validators) {
            errors.merge(validator.validate(readRows));
        }
        return errors;
    }
}
