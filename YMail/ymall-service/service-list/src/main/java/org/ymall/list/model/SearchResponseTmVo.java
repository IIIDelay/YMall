/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.list.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchResponseTmVo implements Serializable {

    // 当前属性值的所有值
    private Long tmId;
    // 属性名称
    private String tmName;// 网络制式，分类
    // 图片名称
    private String tmLogoUrl;// 网络制式，分类

}
