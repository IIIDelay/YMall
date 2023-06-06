/*
 * Copyright (c) 2023, author: IIIDev
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
