/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 库存单元表(SkuInfo)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:41
 */
@TableName("sku_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuInfo extends Model<SkuInfo> {
    // 库存id(itemID)
    private Long id;
    // 商品id
    private Long spuId;
    // 价格
    private BigDecimal price;
    // sku名称
    private String skuName;
    // 商品规格描述
    private String skuDesc;
    // 重量
    private BigDecimal weight;
    // 品牌(冗余)
    private Long tmId;
    // 三级分类id (冗余)
    private Long category3Id;
    // 默认显示图片(冗余)
    private String skuDefaultImg;
    // 是否销售 (1：是 0：否)
    private Integer isSale;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;

    @TableField(exist = false)
    private List<SkuImage> skuImageList;

    @TableField(exist = false)
    private List<SkuSaleAttrValue> skuSaleAttrValueList;

    @TableField(exist = false)
    private List<SkuAttrValue> skuAttrValueList;
}

