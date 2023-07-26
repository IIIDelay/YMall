/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.valid.test.validator;

import org.springframework.stereotype.Component;
import org.valid.test.model.DemoData;
import org.ymall.easy.validator.ExcelValidator;
import org.ymall.easy.validator.ReadRow;
import org.ymall.easy.validator.ReadRows;
import org.ymall.easy.validator.errors.DefaultExcelObjectError;
import org.ymall.easy.validator.errors.ExcelValidErrors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomExcelValidator implements ExcelValidator<DemoData> {
    @Override
    public ExcelValidErrors validate(ReadRows<DemoData> readRows) {
        ExcelValidErrors errors = new ExcelValidErrors();

        Map<Integer, List<ReadRow<DemoData>>> group = readRows.getRows().stream()
                .collect(Collectors.groupingBy(item -> item.getData().getInteger()));

        for (Map.Entry<Integer, List<ReadRow<DemoData>>> entry : group.entrySet()) {
            if (entry.getValue().size() > 1) {
                for (ReadRow<DemoData> readRow : entry.getValue()) {
                    errors.addError(new DefaultExcelObjectError(readRow.getRowIndex() + 1, "参数重复"));
                }
            }
        }
        return errors;
    }
}
