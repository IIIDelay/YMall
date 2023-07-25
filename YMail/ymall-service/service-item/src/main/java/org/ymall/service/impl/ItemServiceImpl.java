/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.service.impl;

import com.alibaba.fastjson.JSON;
import org.iiidev.ymall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ymall.list.client.ListFeignClient;
import org.ymall.model.product.BaseAttrInfo;
import org.ymall.model.product.BaseCategoryView;
import org.ymall.model.product.SkuInfo;
import org.ymall.model.product.SpuPoster;
import org.ymall.model.product.SpuSaleAttr;
import org.ymall.service.ItemService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    //  远程调用service-product-client
    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private ThreadPoolExecutor productThreadPool;

    @Autowired
    private ListFeignClient listFeignClient;

    @Override
    public Map<String, Object> getItemBySkuId(Long skuId) {
        Map<String, Object> result = new ConcurrentHashMap<>();

        // 通过skuId 查询skuInfo
        CompletableFuture<SkuInfo> skuCompletableFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
            // 保存skuInfo
            result.put("skuInfo", skuInfo);
            return skuInfo;
        }, productThreadPool);


        // 销售属性-销售属性值回显并锁定
        CompletableFuture<Void> spuSaleAttrCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            List<SpuSaleAttr> spuSaleAttrList = productFeignClient.getSpuSaleAttrListCheckBySku(skuInfo.getId(), skuInfo.getSpuId());

            // 保存数据
            result.put("spuSaleAttrList", spuSaleAttrList);
        }, productThreadPool);

        // 根据spuId 查询map 集合属性
        // 销售属性-销售属性值回显并锁定
        CompletableFuture<Void> skuValueIdsMapCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            Map skuValueIdsMap = productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId());

            String valuesSkuJson = JSON.toJSONString(skuValueIdsMap);
            // 保存valuesSkuJson
            result.put("valuesSkuJson", valuesSkuJson);
        }, productThreadPool);


        // 获取商品最新价格
        CompletableFuture<Void> skuPriceCompletableFuture = CompletableFuture.runAsync(() -> {
            BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
            result.put("price", skuPrice);
        }, productThreadPool);


        // 获取分类信息
        CompletableFuture<Void> categoryViewCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());

            // 分类信息
            result.put("categoryView", categoryView);
        }, productThreadPool);

        //  获取海报数据
        CompletableFuture<Void> spuPosterListCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            //  spu海报数据
            List<SpuPoster> spuPosterList = productFeignClient.getSpuPosterBySpuId(skuInfo.getSpuId());
            result.put("spuPosterList", spuPosterList);
        }, productThreadPool);

        //  获取sku平台属性，即规格数据
        CompletableFuture<Void> skuAttrListCompletableFuture = CompletableFuture.runAsync(() -> {
            List<BaseAttrInfo> attrList = productFeignClient.getAttrList(skuId);
            //  使用拉姆达表示
            List<Map<String, String>> skuAttrList = attrList.stream().map((baseAttrInfo) -> {
                Map<String, String> attrMap = new HashMap<>();
                attrMap.put("attrName", baseAttrInfo.getAttrName());
                attrMap.put("attrValue", baseAttrInfo.getAttrValueList().get(0).getValueName());
                return attrMap;
            }).collect(Collectors.toList());
            result.put("skuAttrList", skuAttrList);
        }, productThreadPool);

        //更新商品incrHotScore
        CompletableFuture<Void> incrHotScoreCompletableFuture = CompletableFuture.runAsync(() -> {
            listFeignClient.incrHotScore(skuId);
        }, productThreadPool);


        CompletableFuture.allOf(skuCompletableFuture, spuSaleAttrCompletableFuture, skuValueIdsMapCompletableFuture, skuPriceCompletableFuture, categoryViewCompletableFuture, spuPosterListCompletableFuture, skuAttrListCompletableFuture).join();
        return result;
    }
}

