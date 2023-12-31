/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResponse {

    String fileName() default "default";

    String sheetName() default "Sheet1";

}
