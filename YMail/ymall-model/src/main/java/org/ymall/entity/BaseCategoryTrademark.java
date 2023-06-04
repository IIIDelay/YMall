/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 三级分类表(BaseCategoryTrademark)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:40
 */
@TableName("base_category_trademark")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCategoryTrademark extends Model<BaseCategoryTrademark> {
    // 编号
    private Long id;
    // 三级级分类id
    private Long category3Id;
    // 品牌id
    private Long trademarkId;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

