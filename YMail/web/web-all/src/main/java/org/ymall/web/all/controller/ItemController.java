/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.web.all.controller;

import org.iiidev.ymall.item.client.ItemFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import result.Result;
import result.ServiceResponse;

import java.util.Map;

@Controller
public class ItemController {

    @Autowired
    private ItemFeignClient itemFeignClient;

    /**
     * sku详情页面
     *
     * @param skuId
     * @param model
     * @return
     */
    @RequestMapping("{skuId}.html")
    public String getItem(@PathVariable Long skuId, Model model) {
        // 通过skuId 查询skuInfo
        ServiceResponse<Map> result = itemFeignClient.getItem(skuId);
        model.addAllAttributes(result.getData());
        return "item/index";
    }
}
