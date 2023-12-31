/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.list.client.impl;

import org.springframework.stereotype.Component;
import org.ymall.list.client.ListFeignClient;
import org.ymall.model.list.SearchParam;
import org.iiidev.ymall.result.Result;

@Component
public class ListDegradeFeignClient implements ListFeignClient {

    @Override
    public Result incrHotScore(Long skuId) {
        return null;
    }


    @Override
    public Result list(SearchParam searchParam) {
        return Result.fail();
    }

    @Override
    public Result upperGoods(Long skuId) {
        return null;
    }

    @Override
    public Result lowerGoods(Long skuId) {
        return null;
    }

}
