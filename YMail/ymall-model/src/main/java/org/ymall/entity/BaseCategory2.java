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
 * 二级分类表(BaseCategory2)表实体类
 *
 * @author IIIDev
 * @since 2023-03-02 21:57:35
 */
@TableName("base_category2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCategory2 extends Model<BaseCategory2> {
    // 编号
    private Long id;
    // 二级分类名称
    private String name;
    // 一级分类编号
    private Long category1Id;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

