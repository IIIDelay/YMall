/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.product.client;

import org.iiidev.ymall.product.client.impl.ProductDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.ymall.model.product.BaseAttrInfo;
import org.ymall.model.product.BaseCategoryView;
import org.ymall.model.product.BaseTrademark;
import org.ymall.model.product.SkuInfo;
import org.ymall.model.product.SpuPoster;
import org.ymall.model.product.SpuSaleAttr;
import org.iiidev.ymall.result.ServiceResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(value = "service-product", fallback = ProductDegradeFeignClient.class)
public interface ProductFeignClient {

    /**
     * 根据skuId获取sku信息
     *
     * @param skuId
     * @return
     */
    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    SkuInfo getSkuInfo(@PathVariable("skuId") Long skuId);


    /**
     * 通过三级分类id查询分类信息
     *
     * @param category3Id
     * @return
     */
    @GetMapping("/api/product/inner/getCategoryView/{category3Id}")
    BaseCategoryView getCategoryView(@PathVariable("category3Id") Long category3Id);

    /**
     * 获取sku最新价格
     *
     * @param skuId
     * @return
     */
    @GetMapping("/api/product/inner/getSkuPrice/{skuId}")
    BigDecimal getSkuPrice(@PathVariable(value = "skuId") Long skuId);

    /**
     * 根据spuId，skuId 查询销售属性集合
     *
     * @param skuId
     * @param spuId
     * @return
     */
    @GetMapping("/api/product/inner/getSpuSaleAttrListCheckBySku/{skuId}/{spuId}")
    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(@PathVariable("skuId") Long skuId, @PathVariable("spuId") Long spuId);

    /**
     * 根据spuId 查询map 集合属性
     *
     * @param spuId
     * @return
     */
    @GetMapping("/api/product/inner/getSkuValueIdsMap/{spuId}")
    Map getSkuValueIdsMap(@PathVariable("spuId") Long spuId);

    //  根据spuId 获取海报数据
    @GetMapping("/api/product/inner/findSpuPosterBySpuId/{spuId}")
    List<SpuPoster> getSpuPosterBySpuId(@PathVariable Long spuId);

    /**
     * 通过skuId 集合来查询数据
     *
     * @param skuId
     * @return
     */
    @GetMapping("/api/product/inner/getAttrList/{skuId}")
    List<BaseAttrInfo> getAttrList(@PathVariable("skuId") Long skuId);

    /**
     * 获取全部分类信息
     *
     * @return
     */
    @GetMapping("/api/product/getBaseCategoryList")
    ServiceResponse getBaseCategoryList();

    /**
     * 通过品牌Id 集合来查询数据
     * @param tmId
     * @return
     */
    @GetMapping("/api/product/inner/getTrademark/{tmId}")
    BaseTrademark getTrademark(@PathVariable("tmId") Long tmId);

}
