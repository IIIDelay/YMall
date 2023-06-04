/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ymall.entity.BaseTrademark;
import org.ymall.service.IBaseTrademarkService;
import result.ServiceResponse;

@RestController
@RequestMapping("/admin/product/baseTrademark")
public class BaseTrademarkController {

    @Autowired
    private IBaseTrademarkService baseTrademarkService;

    @ApiOperation(value = "分页列表")
    @GetMapping("{page}/{limit}")
    public ServiceResponse<IPage<BaseTrademark>> index(@PathVariable Long page,
                                                       @PathVariable Long limit) {

        Page<BaseTrademark> pageParam = new Page<>(page, limit);
        IPage<BaseTrademark> pageModel = baseTrademarkService.getPage(pageParam);
        return ServiceResponse.ok(pageModel);
    }

    @ApiOperation(value = "获取BaseTrademark")
    @GetMapping("get/{id}")
    public ServiceResponse<BaseTrademark> get(@PathVariable String id) {
        BaseTrademark baseTrademark = baseTrademarkService.getById(id);
        return ServiceResponse.ok(baseTrademark);
    }

    @ApiOperation(value = "新增BaseTrademark")
    @PostMapping("save")
    public ServiceResponse<Void> save(@RequestBody BaseTrademark banner) {
        baseTrademarkService.save(banner);
        return ServiceResponse.ok();
    }

    @ApiOperation(value = "修改BaseTrademark")
    @PutMapping("update")
    public ServiceResponse<Void> updateById(@RequestBody BaseTrademark banner) {
        baseTrademarkService.updateById(banner);
        return ServiceResponse.ok();
    }

    @ApiOperation(value = "删除BaseTrademark")
    @DeleteMapping("remove/{id}")
    public ServiceResponse<Void> remove(@PathVariable Long id) {
        baseTrademarkService.removeById(id);
        return ServiceResponse.ok();
    }

}
