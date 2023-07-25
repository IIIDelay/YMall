/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package utils;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ReflectHelper
 *
 * @Author IIIDelay
 * @Date 2023/7/25 20:57
 **/
public class ReflectHelper {
    /**
     * getAllField
     *
     * @param inClass     inClass
     * @param ignoreNames ignoreNames
     * @return List<Field>
     */
    public <IN> List<Field> getAllField(Class<IN> inClass, Set<String> ignoreNames) {
        return FieldUtils.getAllFieldsList(inClass).stream().filter(field -> !ignoreNames.contains(field)).collect(Collectors.toList());
    }

    /**
     * getFieldMap
     *
     * @param inClass     inClass
     * @param ignoreNames ignoreNames
     * @return Map<String, Field>
     */
    public <IN> Map<String, Field> getFieldMap(Class<IN> inClass, Set<String> ignoreNames) {
        return getAllField(inClass, Collections.emptySet())
            .stream().collect(Collectors.toMap(Field::getName, Function.identity()));
    }

    public void xx () {
        PropertyDescriptor pd = new PropertyDescriptor();
    }

}
