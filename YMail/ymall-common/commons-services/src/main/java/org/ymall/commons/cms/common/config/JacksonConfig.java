

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 处理LocalDate、LocalDateTime、Long 三种类型JSON序列化
 *

 * @since 2021/04/02 20:53
 **/
public class JacksonConfig {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * LocalDate序列化配置
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer localDateCustomizer() {
        return builder -> builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
    }

    /**
     * LocalDateTime序列化配置
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer localDateTimeCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
    }

    /**
     * Long序列化配置
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer longCustomizer() {
        return builder -> builder.serializerByType(Long.class, ToStringSerializer.instance);
    }
}
