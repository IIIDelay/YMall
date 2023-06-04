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
 * 库存单元图片表(SkuImage)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:41
 */
@TableName("sku_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuImage extends Model<SkuImage> {
    // 编号
    private Long id;
    // 商品id
    private Long skuId;
    // 图片名称 (冗余）
    private String imgName;
    // 图片路径(冗余)
    private String imgUrl;
    // 商品图片id
    private Long spuImgId;
    // 是否默认
    private String isDefault;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

