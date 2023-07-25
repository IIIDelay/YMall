/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.service;


import org.ymall.entity.UserInfo;

public interface IUserService {

    /**
     * 登录方法
     * @param userInfo
     * @return
     */
    UserInfo login(UserInfo userInfo);

}
