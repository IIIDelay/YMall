/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import lombok.Data;

import java.io.Serializable;

/**
 * BookDoc
 *
 * @Author IIIDelay
 * @Date 2023/9/20 22:12
 **/
@Data
public class BookDoc extends CommMongoRepository<String> implements Serializable {
    private String name;

    private Integer age;
}
