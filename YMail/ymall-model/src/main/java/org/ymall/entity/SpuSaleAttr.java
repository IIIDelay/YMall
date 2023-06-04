/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * spu销售属性(SpuSaleAttr)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:41
 */
@TableName("spu_sale_attr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuSaleAttr{
    // 编号(业务中无关联)
    private Long id;
    // 商品id
    private Long spuId;
    // 销售属性id
    private Long baseSaleAttrId;
    // 销售属性名称(冗余)
    private String saleAttrName;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;

    @TableField(exist = false)
    List<SpuSaleAttrValue> spuSaleAttrValueList;
}

