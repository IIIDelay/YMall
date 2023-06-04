package org.ymall.commons;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author atguigu-mqx
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface YMallCache {

    //  定义一个数据 sku:skuId
    //  目的用这个前缀要想组成 缓存的key！
    String prefix() default "cache:";

}
