/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.impl;

import org.iiidev.ymall.UserFeignClient;
import org.springframework.stereotype.Component;
import org.ymall.model.user.UserAddress;

import java.util.List;

@Component
public class UserDegradeFeignClient implements UserFeignClient {


    @Override
    public List<UserAddress> findUserAddressListByUserId(String userId) {
        return null;
    }
}
