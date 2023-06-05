package org.iiidev.ymall.product.client.impl;

import org.iiidev.ymall.product.client.ProductFeignClient;
import org.springframework.stereotype.Component;
import org.ymall.entity.BaseAttrInfo;
import org.ymall.entity.BaseCategoryView;
import org.ymall.entity.BaseTrademark;
import org.ymall.entity.SkuInfo;
import org.ymall.entity.SpuPoster;
import org.ymall.entity.SpuSaleAttr;
import result.Result;
import result.ServiceResponse;

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
