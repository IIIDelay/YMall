/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.cart.client.impl;

import org.ymall.cart.client.CartFeignClient;

@Component
public class CartDegradeFeignClient implements CartFeignClient {
    @Override
    public List<CartInfo> getCartCheckedList(String userId) {
        return null;
    }
}
