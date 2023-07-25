/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.web.all.controller;

import org.iiidev.ymall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.ymall.model.product.SkuInfo;

@Controller
public class CartController {


    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 添加购物车后跳转页面
     * @return
     *
     * 页面需要的数据：
     *          //商品的图片
     *         //商品的名称
     *         //商品的数量
     *         //商品的id
     *
     *         1.skuInfo对象
     *         2.本次加入购物车的数量
     *
     */
    @GetMapping("/addCart.html")
    public String addCart(Long skuId, Integer skuNum, Model model){

        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        //响应
        model.addAttribute("skuInfo",skuInfo);
        model.addAttribute("skuNum",skuNum);


        return "cart/addCart";
    }


    /**
     * 跳转到展示页面
     * @return
     */
    @GetMapping("/cart.html")
    public String index(){

        return "cart/index";
    }
}
