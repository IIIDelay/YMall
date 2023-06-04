/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ymall.entity.BaseAttrInfo;
import org.ymall.entity.BaseAttrValue;
import org.ymall.entity.BaseCategory1;
import org.ymall.entity.BaseCategory2;
import org.ymall.entity.BaseCategory3;
import org.ymall.entity.SkuInfo;
import org.ymall.service.IManageService;
import result.ServiceResponse;

import java.util.List;

@Api(tags = "商品基础属性接口")
@RestController
@RequestMapping("admin/product")
public class BaseManageController {

    @Autowired
    private IManageService manageService;

    /**
     * getCategory1 查询所有的一级分类信息
     *
     * @return ServiceResponse<List < BaseCategory1>>
     */
    @GetMapping("getCategory1")
    @ApiOperation(value = "一级分类", notes = "获取所有一级分类信息")
    public ServiceResponse<List<BaseCategory1>> getCategory1() {
        List<BaseCategory1> baseCategory1List = manageService.getCategory1();
        return ServiceResponse.ok(baseCategory1List);
    }

    /**
     * 根据一级分类Id 查询二级分类数据
     *
     * @param category1Id
     * @return
     */
    @GetMapping("getCategory2/{category1Id}")
    public ServiceResponse<List<BaseCategory2>> getCategory2(@PathVariable("category1Id") Long category1Id) {
        List<BaseCategory2> baseCategory2List = manageService.getCategory2(category1Id);
        return ServiceResponse.ok(baseCategory2List);
    }

    /**
     * 根据二级分类Id 查询三级分类数据
     *
     * @param category2Id
     * @return
     */
    @GetMapping("getCategory3/{category2Id}")
    public ServiceResponse<List<BaseCategory3>> getCategory3(@PathVariable("category2Id") Long category2Id) {
        List<BaseCategory3> baseCategory3List = manageService.getCategory3(category2Id);
        return ServiceResponse.ok(baseCategory3List);
    }

    /**
     * 根据分类Id 获取平台属性数据
     *
     * @param category1Id
     * @param category2Id
     * @param category3Id
     * @return
     */
    @GetMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public ServiceResponse<List<BaseAttrInfo>> attrInfoList(@PathVariable("category1Id") Long category1Id,
                                                            @PathVariable("category2Id") Long category2Id,
                                                            @PathVariable("category3Id") Long category3Id) {
        List<BaseAttrInfo> baseAttrInfoList = manageService.getAttrInfoList(category1Id, category2Id, category3Id);
        return ServiceResponse.ok(baseAttrInfoList);
    }

    /**
     * 保存平台属性方法
     *
     * @param baseAttrInfo
     * @return
     */
    @PostMapping("saveAttrInfo")
    public ServiceResponse saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo) {
        // 前台数据都被封装到该对象中baseAttrInfo


        manageService.saveAttrInfo(baseAttrInfo);
        return ServiceResponse.ok();
    }

    @GetMapping("getAttrValueList/{attrId}")
    public ServiceResponse<List<BaseAttrValue>> getAttrValueList(@PathVariable("attrId") Long attrId) {
        BaseAttrInfo baseAttrInfo = manageService.getAttrInfo(attrId);
        List<BaseAttrValue> baseAttrValueList = baseAttrInfo.getAttrValueList();
        return ServiceResponse.ok(baseAttrValueList);
    }

    /**
     * SKU分页列表
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/list/{page}/{limit}")
    public ServiceResponse index(
        @PathVariable Long page,
        @PathVariable Long limit) {

        Page<SkuInfo> pageParam = new Page<>(page, limit);
        IPage<SkuInfo> pageModel = manageService.getPage(pageParam);
        return ServiceResponse.ok(pageModel);
    }

    /**
     * 商品上架
     *
     * @param skuId
     * @return
     */
    @GetMapping("onSale/{skuId}")
    public ServiceResponse onSale(@PathVariable("skuId") Long skuId) {
        manageService.onSale(skuId);
        return ServiceResponse.ok();
    }

    /**
     * 商品下架
     *
     * @param skuId
     * @return
     */
    @GetMapping("cancelSale/{skuId}")
    public ServiceResponse cancelSale(@PathVariable("skuId") Long skuId) {
        manageService.cancelSale(skuId);
        return ServiceResponse.ok();
    }

    @Data// 会导致, springmvc处理请求时, 认为没有有参构造器, 内部类加 @AllArgsConstructor 慎重
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String name;
        private int gender;
    }

}
