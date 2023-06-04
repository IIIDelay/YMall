/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 一级分类表(BaseCategory1)表实体类
 *
 * @author IIIDev
 * @since 2023-03-01 22:14:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_category1")
public class BaseCategory1 extends Model<BaseCategory1> {
    // 编号
    @TableId
    private Long id;
    // 分类名称
    private String name;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

