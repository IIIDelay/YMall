
/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.Annotation;

import org.iiidev.ymall.common.UnitConvertType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JcBigDecConvert {

    UnitConvertType name();
}