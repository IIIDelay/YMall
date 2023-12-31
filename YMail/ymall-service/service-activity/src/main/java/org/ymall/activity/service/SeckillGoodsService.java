/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.activity.service;

import org.ymall.model.activity.SeckillGoods;

import java.util.List;

public interface SeckillGoodsService {
    /**
     * 查询秒杀列表
     * @return
     */
    List<SeckillGoods> findAll();

    /**
     * 商品详情
     * @param skuId
     * @return
     */
    SeckillGoods getSeckillGoods(Long skuId);

    /**
     * 抢单
     * @param userId
     * @param skuId
     */
    void seckillOrder(String userId, Long skuId);

}
