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
 * 三级分类表(BaseCategory3)表实体类
 *
 * @author IIIDev
 * @since 2023-03-02 22:01:13
 */
@TableName("base_category3")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCategory3 extends Model<BaseCategory3> {
    // 编号
    private Long id;
    // 三级分类名称
    private String name;
    // 二级分类编号
    private Long category2Id;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

