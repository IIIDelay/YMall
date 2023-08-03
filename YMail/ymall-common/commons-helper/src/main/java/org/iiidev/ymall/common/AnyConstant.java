/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.common;

/**
 * AnyConstant
 *
 * @Author IIIDelay
 * @Date 2023/8/3 23:15
 **/
public interface AnyConstant {
    /**
     * content-type:
     * 方式1: {@link org.apache.http.entity.ContentType}
     * 方式2: {@link cn.hutool.http.ContentType}
     * 方式3: {@link org.springframework.http.MediaType}
     */

    /**
     * request-header:
     * 方式1: {@link cn.hutool.http.Header}
     * 方式2: {@link org.apache.http.HttpHeaders}
     * 方式3: {@link org.springframework.http.HttpHeaders}
     * 方式4: {@link com.google.common.net.HttpHeaders}
     */

    interface FileExt {
        String txt = ".txt";
        String excel2003 = ".xls";
        String excel2007 = ".xlsx";
        String jar = ".jar";
        String clazz = ".class";
        String java = ".java";
        String js = ".javascript";
    }
}
