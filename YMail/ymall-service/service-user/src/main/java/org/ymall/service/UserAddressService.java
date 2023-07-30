/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.service;

import org.ymall.entity.UserAddress;

import java.util.List;

public interface UserAddressService {

    /**
     * 根据用户Id 查询用户的收货地址列表！
          * @param userId
     * @return
     */
    List<UserAddress> findUserAddressListByUserId(String userId);
}
