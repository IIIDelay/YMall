/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.list.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResponseAttrVo implements Serializable {

    private Long attrId;// 1
    // 当前属性值的所有值
    private List<String> attrValueList = new ArrayList<>();
    // 属性名称
    private String attrName;// 网络制式，分类
}
