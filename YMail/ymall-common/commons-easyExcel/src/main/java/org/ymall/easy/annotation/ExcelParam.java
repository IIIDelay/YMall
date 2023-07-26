package org.ymall.easy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelParam {

    /**
     * 字段名称
     *
     * @return
     */
    String value() default "file";

    /**
     * 是否必须
     *
     * @return
     */
    boolean required() default true;

}
