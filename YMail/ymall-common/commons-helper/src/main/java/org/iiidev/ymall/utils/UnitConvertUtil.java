/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import org.iiidev.ymall.Annotation.JcBigDecConvert;
import org.iiidev.ymall.common.UnitConvertType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class UnitConvertUtil {

    /**
     * unitMapConvert 单位转换
     *
     * @param inList      inList
     * @param propertyMap propertyMap
     */
    public static <IN> void unitMapConvert(List<IN> inList, Map<String, UnitConvertType> propertyMap) {
        for (IN in : inList) {
            Field[] declaredFields = in.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (propertyMap.keySet().stream().anyMatch(key -> key.equals(declaredField.getName()))) {
                    try {
                        declaredField.setAccessible(true);
                        Object o = declaredField.get(in);
                        UnitConvertType unitConvertType = propertyMap.get(declaredField.getName());
                        if (o != null) {
                            if (unitConvertType.equals(UnitConvertType.PERCENTAGE)) {
                                BigDecimal bigDecimal = ((BigDecimal) o).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                                declaredField.set(in, bigDecimal);
                            }
                            if (unitConvertType.equals(UnitConvertType.PERMIL)) {
                                BigDecimal bigDecimal = ((BigDecimal) o).multiply(new BigDecimal(1000)).setScale(2, BigDecimal.ROUND_HALF_UP);
                                declaredField.set(in, bigDecimal);
                            }
                            if (unitConvertType.equals(UnitConvertType.B)) {
                                BigDecimal bigDecimal = ((BigDecimal) o).divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_HALF_UP);
                                declaredField.set(in, bigDecimal);
                            }
                            if (unitConvertType.equals(UnitConvertType.R)) {
                                BigDecimal bigDecimal = ((BigDecimal) o).setScale(2, BigDecimal.ROUND_HALF_UP);
                                declaredField.set(in, bigDecimal);
                            }
                        }
                    } catch (Exception ex) {
                        log.error("处理失败");
                        continue;
                    }

                }
            }
        }
    }

    /**
     * unitAnnotateConvert: 注解处理单位转换
     *
     * @param list list
     */
    public static <T> void unitAnnotateConvert(List<T> list) {
        for (T t : list) {
            Field[] declaredFields = t.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                try {
                    if (declaredField.getName().equals("serialVersionUID")){
                        continue;
                    }
                    JcBigDecConvert myFieldAnn = declaredField.getAnnotation(JcBigDecConvert.class);
                    if(Objects.isNull(myFieldAnn)){
                        continue;
                    }
                    UnitConvertType unitConvertType = myFieldAnn.name();
                    declaredField.setAccessible(true);
                    Object o = declaredField.get(t);
                    if (Objects.nonNull(o)) {
                        if (unitConvertType.equals(UnitConvertType.PERCENTAGE)) {
                            BigDecimal bigDecimal = ((BigDecimal) o).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                            declaredField.set(t, bigDecimal);
                        }
                        if (unitConvertType.equals(UnitConvertType.PERMIL)) {
                            BigDecimal bigDecimal = ((BigDecimal) o).multiply(new BigDecimal(1000)).setScale(2, BigDecimal.ROUND_HALF_UP);
                            declaredField.set(t, bigDecimal);
                        }
                        if (unitConvertType.equals(UnitConvertType.B)) {
                            BigDecimal bigDecimal = ((BigDecimal) o).divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_HALF_UP);
                            declaredField.set(t, bigDecimal);
                        }
                        if (unitConvertType.equals(UnitConvertType.R)) {
                            BigDecimal bigDecimal = ((BigDecimal) o).setScale(2, BigDecimal.ROUND_HALF_UP);
                            declaredField.set(t, bigDecimal);
                        }
                    }
                } catch (Exception ex) {
                    log.error("处理失败");
                }
            }
        }
    }

}