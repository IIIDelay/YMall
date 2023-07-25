

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Jackson工具类
 * 需要运行在Spring生态中
 *

 * @since 2021/03/24 13:39
 **/
public class JacksonUtils {
    /**
     * 从Spring容器中获取ObjectMapper实例
     */
    private final static ObjectMapper OBJECT_MAPPER = SpringUtils.getBean(ObjectMapper.class);

    /**
     * 把JavaBean转换为json字符串 抛出异常
     * 本方法是{@link JacksonUtils#writeValueAsString(Object)}的别名方法
     */
    public static String writeValue(Object obj) {
        return writeValueAsString(obj);
    }

    /**
     * 将对象JSON序列化为字符串
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String writeValueAsString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将对象JSON序列化成字节数组
     */
    public static byte[] writeValueAsBytes(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 将json字符串反序列化成对象
     * 本方法是{@link JacksonUtils#readObjectValue(String, Class)}别名方法
     *
     * @param jsonValue jsonValue
     * @param clazz     class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T parse(String jsonValue, Class<T> clazz) {
        return readObjectValue(jsonValue, clazz);
    }

    /**
     * 将json字符串反序列化成对象
     * 本方法是{@link JacksonUtils#readObjectValue(String, Class)}别名方法
     *
     * @param jsonValue jsonValue
     * @param clazz     class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T readValue(String jsonValue, Class<T> clazz) {
        return readObjectValue(jsonValue, clazz);
    }

    /**
     * 读取{@link List}集合类型JSON字符串
     *
     * @param jsonValue
     * @param collClazz
     * @param elementClazz
     * @param <T>
     * @return
     */
    public static <T> List<T> readValue(String jsonValue, Class<? extends List> collClazz, Class<T> elementClazz) {
        return readListValue(jsonValue, elementClazz);
    }


    /**
     * 将json字符串反序列化成对象
     *
     * @param jsonValue jsonValue
     * @param clazz     class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T readObjectValue(String jsonValue, Class<T> clazz) {
        return Optional.ofNullable(jsonValue).map(f -> {
            try {
                return OBJECT_MAPPER.readValue(f, clazz);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).orElse(null);
    }

    public static <T> T readObjectValue(byte[] bytes, Class<T> clazz) {
        return Optional.ofNullable(bytes).map(f -> {
            try {
                return OBJECT_MAPPER.readValue(f, clazz);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).orElse(null);
    }

    public static <T> T readObjectValue(InputStream is, Class<T> clazz) {
        try {
            if (is.available() != 0) {
                return OBJECT_MAPPER.readValue(is, clazz);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static <T> T readObjectValue(String jsonValue, JavaType javaType) {
        if (StringUtils.hasLength(jsonValue)) {
            try {
                return OBJECT_MAPPER.readValue(jsonValue, javaType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    /**
     * 读取{@link List}集合类型JSON字符串
     *
     * @param jsonValue
     * @param elementClazz
     * @param <T>
     * @return
     */
    public static <T> List<T> readListValue(String jsonValue, Class<T> elementClazz) {
        return readObjectValue(jsonValue, getJavaType(List.class, elementClazz));
    }

    /**
     * 获取{@link JavaType}对象
     *
     * @param collectionClazz 集合Class对象
     * @param elementClazz    元素Class对象
     * @param <T>             元素类型
     * @return {@link JavaType}对象实例
     */
    public static <T> JavaType getJavaType(Class<? extends Collection> collectionClazz, Class<T> elementClazz) {
        return OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, elementClazz);
    }

    /**
     * 读取{@link List}集合类型JSON字符串
     *
     * @param bytes
     * @param elementClazz
     * @param <T>
     * @return
     */
    public static <T> List<T> readListValue(byte[] bytes, Class<T> elementClazz) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, elementClazz);
        if (javaType != null) {
            try {
                return OBJECT_MAPPER.readValue(bytes, javaType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static <T> List<T> readListValue(InputStream is, Class<T> elementClazz) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, elementClazz);
        try {
            if (is.available() != 0 && javaType != null) {
                return OBJECT_MAPPER.readValue(is, javaType);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    /**
     * 使用JSON序列化的方式克隆对象
     * 此方式对原始对象无任何要求，属于深拷贝
     *
     * @param obj   原始对象实例
     * @param clazz 原始对象类对象
     * @param <T>   原始对象泛型
     * @return 原始对象深拷贝实例
     */
    public static <T> T clone(T obj, Class<T> clazz) {
        return readObjectValue(writeValue(obj), clazz);
    }
}
