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
 * 基本销售属性表(BaseSaleAttr)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:40
 */
@TableName("base_sale_attr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSaleAttr extends Model<BaseSaleAttr> {
    // 编号
    private Long id;
    // 销售属性名称
    private String name;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

