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
 * 属性值表(BaseAttrValue)表实体类
 *
 * @author IIIDev
 * @since 2023-03-02 22:02:27
 */
@TableName("base_attr_value")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseAttrValue extends Model<BaseAttrValue> {
    // 编号
    private Long id;
    // 属性值名称
    private String valueName;
    // 属性id
    private Long attrId;
    // 创建时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
}

