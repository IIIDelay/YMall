/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户表(UserInfo)表实体类
 *
 * @author IIIDev
 * @since 2023-03-08 17:33:27
 */
@TableName("user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    // 编号
    private Long id;
    // 用户名称
    private String loginName;
    // 用户昵称
    private String nickName;
    // 用户密码
    private String passwd;
    // 用户姓名
    private String name;
    // 手机号
    private String phoneNum;
    // 邮箱
    private String email;
    // 头像
    private String headImg;
    // 用户级别
    private String userLevel;
    
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private Integer isDeleted;
}

