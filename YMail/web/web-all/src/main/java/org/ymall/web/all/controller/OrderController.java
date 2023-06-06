package org.ymall.web.all.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@SuppressWarnings("all")
public class OrderController {

    @Autowired
    private OrderFeignClient orderFeignClient;

    /**
     * 跳转我的订单
     * @return
     */
    @GetMapping("/myOrder.html")
    public String myOrder(){


        return "order/myOrder";
    }


    /**
     * 去结算
     * @return
     */
    @GetMapping("/trade.html")
    public String trade(Model model){

        Result<Map<String,Object>> trade = orderFeignClient.trade();

        //响应
        model.addAllAttributes(trade.getData());


        return "order/trade";
    }
}
