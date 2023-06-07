package org.ymall.activity.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.ymall.activity.client.impl.ActivityDegradeFeignClient;

@FeignClient(value = "service-activity", fallback = ActivityDegradeFeignClient.class)
public interface ActivityFeignClient {

    /**
     * api/activity/seckill/findAll
     * 查询秒杀列表
     *
     * @return
     */
    @GetMapping("api/activity/seckill/findAll")
    Result findAll();

    /**
     * /api/activity/seckill/getSeckillGoods/{skuId}
     * 获取秒杀商品详情
     *
     * @param skuId
     * @return
     */
    @GetMapping("/api/activity/seckill/getSeckillGoods/{skuId}")
    Result getSeckillGoods(@PathVariable Long skuId);
}
