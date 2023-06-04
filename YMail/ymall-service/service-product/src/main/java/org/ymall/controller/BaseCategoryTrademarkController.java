/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ymall.entity.BaseTrademark;
import org.ymall.entity.vo.CategoryTrademarkVo;
import org.ymall.service.BaseCategoryTrademarkService;
import result.ServiceResponse;

import java.util.List;

@RestController
@RequestMapping("admin/product/baseCategoryTrademark")
public class BaseCategoryTrademarkController {

    @Autowired
    private BaseCategoryTrademarkService baseCategoryTrademarkService;


    @PostMapping("save")
    public ServiceResponse<Void> save(@RequestBody CategoryTrademarkVo categoryTrademarkVo){
        //  保存方法
        baseCategoryTrademarkService.save(categoryTrademarkVo);
        return ServiceResponse.ok();
    }

    @DeleteMapping("remove/{category3Id}/{trademarkId}")
    public ServiceResponse<Void> remove(@PathVariable Long category3Id, @PathVariable Long trademarkId){
        //  调用服务层方法
        baseCategoryTrademarkService.remove(category3Id, trademarkId);
        return ServiceResponse.ok();
    }

    @GetMapping("findTrademarkList/{category3Id}")
    public ServiceResponse<List<BaseTrademark>> findTrademarkList(@PathVariable Long category3Id){
        //  select * from base_trademark
        List<BaseTrademark> list = baseCategoryTrademarkService.findTrademarkList(category3Id);
        //  返回
        return ServiceResponse.ok(list);
    }

    @GetMapping("findCurrentTrademarkList/{category3Id}")
    public ServiceResponse<List<BaseTrademark>> findCurrentTrademarkList(@PathVariable Long category3Id){
        List<BaseTrademark> list = baseCategoryTrademarkService.findCurrentTrademarkList(category3Id);
        //  返回
        return ServiceResponse.ok(list);
    }

}
