/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品表(SpuInfo)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:41
 */
@TableName("spu_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuInfo extends Model<SpuInfo> {
    // 商品id
    private Long id;
    // 商品名称
    private String spuName;
    // 商品描述(后台简述）
    private String description;
    // 三级分类id
    private Long category3Id;
    // 品牌id
    private Long tmId;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;

    @TableField(exist = false)
    private List<SpuImage> spuImageList;

    @TableField(exist = false)
    private List<SpuSaleAttr> spuSaleAttrList;

    @TableField(exist = false)
    private List<SpuPoster> spuPosterList;
}

