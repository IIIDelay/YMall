/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 集合转换器
 *

 **/
public class ListConverter implements Converter<String, List<String>> {
    @Override
    public List<String> convert(String source) {
        List<String> splits = Arrays.asList(source.split(","));
        return new ArrayList<>(splits);
    }
}
