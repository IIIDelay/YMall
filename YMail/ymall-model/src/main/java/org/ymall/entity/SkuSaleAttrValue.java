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
 * sku销售属性值(SkuSaleAttrValue)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:41
 */
@TableName("sku_sale_attr_value")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuSaleAttrValue extends Model<SkuSaleAttrValue> {
    // id
    private Long id;
    // 库存单元id
    private Long skuId;
    // spu_id(冗余)
    private Long spuId;
    // 销售属性值id
    private Long saleAttrValueId;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

