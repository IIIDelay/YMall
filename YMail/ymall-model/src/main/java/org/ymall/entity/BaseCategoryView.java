package org.ymall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (BaseCategoryView)实体类
 *
 * @author makejava
 * @since 2023-06-04 17:05:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCategoryView implements Serializable {
    private static final long serialVersionUID = 862244278099116024L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 编号
     */
    private Long category1Id;

    /**
     * 分类名称
     */
    private String category1Name;

    /**
     * 编号
     */
    private Long category2Id;

    /**
     * 二级分类名称
     */
    private String category2Name;

    /**
     * 编号
     */
    private Long category3Id;

    /**
     * 三级分类名称
     */
    private String category3Name;
}

