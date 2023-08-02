/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SortingField implements Serializable {
    private static final long serialVersionUID = -2852232060215844139L;

    /**
     * 顺序 - 升序
     */
    public static final String ORDER_ASC = "asc";
    /**
     * 顺序 - 降序
     */
    public static final String ORDER_DESC = "desc";

    /**
     * 字段
     */
    private String field;
    /**
     * 顺序
     */
    private String order;
}