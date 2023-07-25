/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.ymall.entity.UserInfo;
import org.ymall.mapper.IUserInfoMapper;
import org.ymall.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    // 调用mapper 层
    @Autowired
    private IUserInfoMapper userInfoMapper;


    @Override
    public UserInfo login(UserInfo userInfo) {
        // select * from userInfo where userName = ? and passwd = ?
        // 注意密码是加密：
        String passwd = userInfo.getPasswd(); //123
        // 将passwd 进行加密
        String newPasswd = DigestUtils.md5DigestAsHex(passwd.getBytes());

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", userInfo.getLoginName());
        queryWrapper.eq("passwd", newPasswd);
        UserInfo info = userInfoMapper.selectOne(queryWrapper);
        if (info != null) {
            return info;
        }
        return null;
    }
}
