/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sku平台属性值关联表(SkuAttrValue)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:41
 */
@TableName("sku_attr_value")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuAttrValue extends Model<SkuAttrValue> {
    // 编号
    private Long id;
    // 属性id (冗余)
    private Long attrId;
    // 属性值id
    private Long valueId;
    // skuid
    private Long skuId;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

