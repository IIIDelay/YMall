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
 * 商品图片表(SpuImage)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:41
 */
@TableName("spu_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuImage extends Model<SpuImage> {
    // 编号
    private Long id;
    // 商品id
    private Long spuId;
    // 图片名称
    private String imgName;
    // 图片路径
    private String imgUrl;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

