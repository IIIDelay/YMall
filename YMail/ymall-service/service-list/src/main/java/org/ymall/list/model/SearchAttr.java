/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.list.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * 平台属性-平台属性值
 */
@Data
public class SearchAttr {

    @Field(type = FieldType.Long)
    private Long attrId;

    @Field(type = FieldType.Keyword)
    private String attrName;

    @Field(type = FieldType.Keyword)
    private String attrValue;
}
