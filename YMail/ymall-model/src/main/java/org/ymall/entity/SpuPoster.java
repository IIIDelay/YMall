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
 * 商品海报表(SpuPoster)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:41
 */
@TableName("spu_poster")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpuPoster extends Model<SpuPoster> {
    // 编号
    private Long id;
    // 商品id
    private Long spuId;
    // 文件名称
    private String imgName;
    // 文件路径
    private String imgUrl;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

