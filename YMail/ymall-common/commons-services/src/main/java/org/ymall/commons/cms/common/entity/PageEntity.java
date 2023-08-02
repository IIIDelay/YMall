/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.entity;

import lombok.Data;

import java.util.List;

/**
 * 分页对象
 */
@Data
public class PageEntity<OUT> {
    /**
     * total 总条数
     */
    private long total;

    /**
     * size 每页数量
     */
    private long size;

    /**
     * current 当前页
     */
    private long current;

    /**
     * totalPageSize 总页数
     */
    private long totalPageSize;

    /**
     * records 分页内容
     */
    private List<OUT> records;
    
    /**
     * hasNextPage 是否有下页
     */
    private boolean hasNextPage;

    public void hasNextPage() {
        if (current*size < total) {
            setHasNextPage(true);
        }
    }
}
