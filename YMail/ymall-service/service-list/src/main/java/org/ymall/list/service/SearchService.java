/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.list.service;

import org.ymall.list.model.SearchResponseVo;
import org.ymall.model.list.SearchParam;

import java.io.IOException;

public interface SearchService {

    /**
     * 上架商品列表
     * @param skuId
     */
    void upperGoods(Long skuId);

    /**
     * 下架商品列表
     * @param skuId
     */
    void lowerGoods(Long skuId);

    /**
     * 更新热点
     * @param skuId
     */
    void incrHotScore(Long skuId);

    /**
     * 搜索列表
     * @param searchParam
     * @return
     * @throws IOException
     */
    SearchResponseVo search(SearchParam searchParam) throws IOException;

}
