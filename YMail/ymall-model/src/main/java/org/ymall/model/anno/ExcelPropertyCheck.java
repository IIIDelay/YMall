/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.model.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExcelPropertyCheck {
    boolean required() default true; // 是否为空，默认不为空。

    boolean checkFormat() default false; // 是否进行格式检验，默认不进行。

    int type() default -1; // 格式检验类型，int 已经支持的类型有 0->ip、1->端口、2->时间日期格式

    int length() default -1; // 长度校验， int 字符串的长度，-1不进行校验

    String[] value();
}
 
