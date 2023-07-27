/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.validator;

import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;

import java.util.ArrayList;
import java.util.List;

public class ReadRows<T> {

    private ExcelReadHeadProperty excelReadHeadProperty;

    private List<ReadRow<T>> rows;

    public ReadRows() {
        this.rows = new ArrayList<>();
    }

    public void setExcelReadHeadProperty(ExcelReadHeadProperty excelReadHeadProperty) {
        this.excelReadHeadProperty = excelReadHeadProperty;
    }

    public void setRows(List<ReadRow<T>> rows) {
        this.rows = rows;
    }

    public ExcelReadHeadProperty getExcelReadHeadProperty() {
        return excelReadHeadProperty;
    }

    public List<ReadRow<T>> getRows() {
        return rows;
    }

    public boolean isEmpty() {
        return rows == null || rows.isEmpty();
    }

    @Override
    public String toString() {
        return "ReadRows{" +
                "excelReadHeadProperty=" + excelReadHeadProperty +
                ", rows=" + rows +
                '}';
    }

}
