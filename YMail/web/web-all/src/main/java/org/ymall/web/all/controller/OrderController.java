/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.web.all.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.ymall.order.client.OrderFeignClient;
import org.iiidev.ymall.result.Result;

import java.util.Map;

@Controller
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class OrderController {

    private final OrderFeignClient orderFeignClient;

    /**
     * 跳转我的订单
     *
     * @return
     */
    @GetMapping("/myOrder.html")
    public String myOrder() {
        return "order/myOrder";
    }


    /**
     * 去结算
     *
     * @return
     */
    @GetMapping("/trade.html")
    public String trade(Model model) {

        Result<Map<String, Object>> trade = orderFeignClient.trade();
        // 响应
        model.addAllAttributes(trade.getData());
        return "order/trade";
    }
}
