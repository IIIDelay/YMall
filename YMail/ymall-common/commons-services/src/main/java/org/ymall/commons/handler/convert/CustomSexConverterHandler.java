/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.handler.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

import java.util.Objects;

/**
 * @ClassName CustomSexConverterHandler
 * @Description 自定义性别类型转换器
 */
public class CustomSexConverterHandler implements Converter<Integer> {
    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 导入
     */
    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) throws Exception {
        return "男".equals(context.getReadCellData().getStringValue()) ? 1 : 0;
    }

    /**
     * 导出
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) throws Exception {
        if (Objects.equals(1, context.getValue())) {
            return new WriteCellData<>("男");
        } else {
            return new WriteCellData("女");
        }
    }
}