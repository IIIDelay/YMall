/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.product.client.impl;

import org.iiidev.ymall.product.client.ProductFeignClient;
import org.springframework.stereotype.Component;
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

@Component
public class ProductDegradeFeignClient implements ProductFeignClient {

    @Override
    public SkuInfo getSkuInfo(Long skuId) {
        return null;
    }

    @Override
    public BaseCategoryView getCategoryView(Long category3Id) {
        return null;
    }

    @Override
    public BigDecimal getSkuPrice(Long skuId) {
        return null;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId, Long spuId) {
        return null;
    }


    @Override
    public Map getSkuValueIdsMap(Long spuId) {
        return null;
    }

    @Override
    public List<SpuPoster> getSpuPosterBySpuId(Long spuId) {
        return null;
    }

    @Override
    public List<BaseAttrInfo> getAttrList(Long skuId) {
        return null;
    }

    @Override
    public ServiceResponse getBaseCategoryList() {
        return null;
    }

    @Override
    public BaseTrademark getTrademark(Long tmId) {
        return null;
    }

}
