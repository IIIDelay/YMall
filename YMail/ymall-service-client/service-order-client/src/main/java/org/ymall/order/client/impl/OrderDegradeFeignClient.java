/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.order.client.impl;

import org.springframework.stereotype.Component;
import org.ymall.model.order.OrderInfo;
import org.ymall.order.client.OrderFeignClient;
import org.iiidev.ymall.result.Result;

import java.util.Map;

@Component
public class OrderDegradeFeignClient implements OrderFeignClient {
    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return null;
    }

    @Override
    public Result<Map<String,Object>> trade() {
        return Result.fail();
    }
}
