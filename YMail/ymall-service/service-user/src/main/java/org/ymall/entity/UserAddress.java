/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户地址表(UserAddress)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 17:33:22
 */
@TableName("user_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    // 编号
    private Long id;
    // 用户id
    private Long userId;
    // 省份id
    private Long provinceId;
    // 用户地址
    private String userAddress;
    // 收件人
    private String consignee;
    // 联系方式
    private String phoneNum;
    // 是否是默认
    private String isDefault;
    // 创建时间
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private Integer isDeleted;
}

