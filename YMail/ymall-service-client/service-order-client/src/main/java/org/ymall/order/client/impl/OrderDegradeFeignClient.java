package org.ymall.order.client.impl;

import org.springframework.stereotype.Component;
import org.ymall.model.order.OrderInfo;
import org.ymall.order.client.OrderFeignClient;
import result.Result;

import java.util.Map;

@Component
public class OrderDegradeFeignClient implements OrderFeignClient {
    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return null;
    }

    @Override
    public Result<Map> trade() {
        return Result.fail();
    }
}
