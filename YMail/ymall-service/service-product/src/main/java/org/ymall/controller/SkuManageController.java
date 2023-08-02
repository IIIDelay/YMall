/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.ymall.model.product.SkuInfo;
import org.ymall.model.product.SpuImage;
import org.ymall.model.product.SpuSaleAttr;
import org.ymall.service.IManageService;
import org.iiidev.ymall.result.ServiceResponse;

import java.math.BigDecimal;
import java.util.List;

@Api(tags = "商品Sku")
@RestController
@RequestMapping("admin/product")
public class SkuManageController {

    @Autowired
    private IManageService manageService;

    /**
     * 根据spuId 查询spuImageList
     *
     * @param spuId
     * @return
     */
    @ApiOperation(value = "根据spuId 查询spuImageList", notes = "根据路径spuImageList/{spuId}查询")
    @GetMapping("spuImageList/{spuId}")
    public ServiceResponse<List<SpuImage>> getSpuImageList(@ApiParam("入参spuId") @PathVariable("spuId") Long spuId) {
        List<SpuImage> spuImageList = manageService.getSpuImageList(spuId);
        return ServiceResponse.ok(spuImageList);
    }

    /**
     * 保存sku
     *
     * @param skuInfo
     * @return
     */
    @PostMapping("saveSkuInfo")
    public ServiceResponse saveSkuInfo(@RequestBody SkuInfo skuInfo) {
        // 调用服务层
        manageService.saveSkuInfo(skuInfo);
        return ServiceResponse.ok();
    }

    /**
     * 根据skuId获取sku信息
     *
     * @param skuId
     * @return
     */
    @GetMapping("inner/getSkuInfo/{skuId}")
    public ServiceResponse<SkuInfo> getAttrValueList(@PathVariable("skuId") Long skuId) {
        return ServiceResponse.ok(manageService.getSkuInfo(skuId));
    }

    /**
     * 获取sku最新价格
     * @param skuId
     * @return
     */
    @GetMapping("inner/getSkuPrice/{skuId}")
    public BigDecimal getSkuPrice(@PathVariable Long skuId){
        return manageService.getSkuPrice(skuId);
    }

    /**
     * 根据spuId，skuId 查询销售属性集合
     * @param skuId
     * @param spuId
     * @return
     */
    @GetMapping("inner/getSpuSaleAttrListCheckBySku/{skuId}/{spuId}")
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(@PathVariable("skuId") Long skuId, @PathVariable("spuId") Long spuId){
        return manageService.getSpuSaleAttrListCheckBySku(skuId, spuId);
    }

}
