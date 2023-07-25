/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.ymall.model.order.OrderInfo;
import org.ymall.order.client.impl.OrderDegradeFeignClient;
import result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(value = "service-order",fallback = OrderDegradeFeignClient.class)
public interface OrderFeignClient {


    /**
     * 根据订单Id 查询订单信息
     * api/order/inner/getOrderInfo/{orderId}
     * @param orderId
     * @return
     */
    @GetMapping("/api/order/inner/getOrderInfo/{orderId}")
     OrderInfo getOrderInfo(@PathVariable Long orderId);
    /**
     * 去结算
     * api/order/auth/trade
     */
    @GetMapping("/api/order/auth/trade")
    Result<Map> trade();

}
