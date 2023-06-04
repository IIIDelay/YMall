package org.ymall.service.impl;/*
 * Copyright (c) 2023, author: IIIDev
 */

import com.alibaba.fastjson.JSON;
import org.iiidev.ymall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ymall.entity.BaseAttrInfo;
import org.ymall.entity.BaseCategoryView;
import org.ymall.entity.SkuInfo;
import org.ymall.entity.SpuPoster;
import org.ymall.entity.SpuSaleAttr;
import org.ymall.service.ItemService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    //  远程调用service-product-client
    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public Map<String, Object> getItemBySkuId(Long skuId) {
        //  声明对象
        Map<String, Object> result = new HashMap<>();

        //  获取到的数据是skuInfo + skuImageList
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);

        //  判断skuInfo 不为空
        if (skuInfo != null) {
            //  获取分类数据
            BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
            result.put("categoryView", categoryView);
            //  获取销售属性+销售属性值
            List<SpuSaleAttr> spuSaleAttrListCheckBySku = productFeignClient.getSpuSaleAttrListCheckBySku(skuId, skuInfo.getSpuId());
            result.put("spuSaleAttrList", spuSaleAttrListCheckBySku);
            //  查询销售属性值Id 与skuId 组合的map
            Map skuValueIdsMap = productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId());
            //  将这个map 转换为页面需要的Json 对象
            String valueJson = JSON.toJSONString(skuValueIdsMap);
            result.put("valuesSkuJson", valueJson);

        }
        //  获取价格
        BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
        //  map 中 key 对应的谁? Thymeleaf 获取数据的时候 ${skuInfo.skuName}
        result.put("skuInfo", skuInfo);
        result.put("price", skuPrice);
        //  返回map 集合 Thymeleaf 渲染：能用map 存储数据！
        //  spu海报数据
        List<SpuPoster> spuPosterList = productFeignClient.getSpuPosterBySpuId(skuInfo.getSpuId());
        result.put("spuPosterList", spuPosterList);
        List<BaseAttrInfo> attrList = productFeignClient.getAttrList(skuId);
        //  使用拉姆达表示
        List<Map<String, String>> skuAttrList = attrList.stream().map((baseAttrInfo) -> {
            Map<String, String> attrMap = new HashMap<>();
            attrMap.put("attrName", baseAttrInfo.getAttrName());
            attrMap.put("attrValue", baseAttrInfo.getAttrValueList().get(0).getValueName());
            return attrMap;
        }).collect(Collectors.toList());
        result.put("skuAttrList", skuAttrList);

        return result;
    }
}

