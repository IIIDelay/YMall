/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.activity.client.impl;

import org.springframework.stereotype.Component;
import org.ymall.activity.client.ActivityFeignClient;
import result.Result;

@Component
public class ActivityDegradeFeignClient implements ActivityFeignClient {
    @Override
    public Result findAll() {
        return Result.fail();
    }

    @Override
    public Result getSeckillGoods(Long skuId) {
        return Result.fail();
    }
}
