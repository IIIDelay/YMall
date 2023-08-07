package org.ymall.learn.easyExcel.annotation;

import java.lang.annotation.*;

/**
 * @author 程强
 * @date 2023/2/13
 * @Description
 * @Copyright (c) 2023 by 程强, by 新疆兵团勘测设计院（集团）有限公司 All Rights Reserved.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ImportExcel {
    /**
     * 字典表映射类路径
     */
    String classPath() default "";
}
