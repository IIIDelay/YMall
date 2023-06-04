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
 * spu销售属性值(SpuSaleAttrValue)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:41
 */
@TableName("spu_sale_attr_value")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuSaleAttrValue extends Model<SpuSaleAttrValue> {
    // 销售属性值编号
    private Long id;
    // 商品id
    private Long spuId;
    // 销售属性id
    private Long baseSaleAttrId;
    // 销售属性值名称
    private String saleAttrValueName;
    // 销售属性名称(冗余)
    private String saleAttrName;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

