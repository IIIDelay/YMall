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
 * 属性表(BaseAttrInfo)表实体类
 *
 * @author IIIDev
 * @since 2023-03-02 22:03:00
 */
@TableName("base_attr_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseAttrInfo extends Model<BaseAttrInfo> {
    // 编号
    private Long id;
    // 属性名称
    private String attrName;
    // 分类id
    private Long categoryId;
    // 分类层级
    private Integer categoryLevel;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;

    @TableField(exist = false)
    private List<BaseAttrValue> attrValueList;
}

