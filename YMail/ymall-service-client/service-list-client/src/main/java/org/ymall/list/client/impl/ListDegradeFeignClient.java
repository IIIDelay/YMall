package org.ymall.list.client.impl;

import org.springframework.stereotype.Component;
import org.ymall.entity.SearchParam;
import org.ymall.list.client.ListFeignClient;
import result.Result;

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
