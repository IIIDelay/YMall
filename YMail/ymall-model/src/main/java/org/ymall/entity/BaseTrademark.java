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
 * 品牌表(BaseTrademark)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 12:23:40
 */
@TableName("base_trademark")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseTrademark extends Model<BaseTrademark> {
    // 编号
    private Long id;
    // 属性值
    private String tmName;
    // 品牌logo的图片路径
    private String logoUrl;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

