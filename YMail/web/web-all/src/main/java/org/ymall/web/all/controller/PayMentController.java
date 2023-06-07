package org.ymall.web.all.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.ymall.model.order.OrderInfo;
import org.ymall.order.client.OrderFeignClient;

@Controller
@SuppressWarnings("all")
public class PayMentController {

    @Autowired
    private OrderFeignClient orderFeignClient;

    /**
     * 跳转到成功界面
     * @return
     */
    @GetMapping("/pay/success.html")
    public String success(){

        return "payment/success";
    }
    /**
     * 显示支付页面
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/pay.html")
    public String pay(Long orderId , Model model){

        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderId);

        model.addAttribute("orderInfo",orderInfo);

        return "payment/pay";

    }
}
