/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.activity.controller;

import common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ymall.activity.service.SeckillGoodsService;
import org.ymall.activity.util.CacheHelper;
import org.ymall.commons.helper.AuthContextHolder;
import org.ymall.model.activity.SeckillGoods;
import org.ymall.model.activity.UserRecode;
import org.ymall.mqs.constant.MqConst;
import org.ymall.mqs.service.RabbitService;
import result.Result;
import utils.DateUtil;
import utils.MD5Util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/activity/seckill")
public class SeckillGoodsController {


    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitService rabbitService;

    /**
     * /api/activity/seckill/auth/seckillOrder/{skuId}
     * 秒杀下单
     *
     * @return
     */
    @PostMapping("/auth/seckillOrder/{skuId}")
    public Result seckillOrder(@PathVariable Long skuId, HttpServletRequest request) {

        // 校验下单码
        String skuIdStr = request.getParameter("skuIdStr");

        // 获取用户id
        String userId = AuthContextHolder.getUserId(request);
        // 校验状态位
        if (MD5Util.encrypt(userId).equals(skuIdStr)) {


            // 校验状态  key :skuId 23 value :状态位 1或者0
            String status = (String) CacheHelper.get(skuId.toString());
            // 判断
            if (StringUtils.isEmpty(status)) {

                return Result.build(null, ResultCodeEnum.SECKILL_ILLEGAL);
            }

            // 判断状态值
            if ("0".equals(status)) {

                return Result.build(null, ResultCodeEnum.SECKILL_FINISH);
            } else {


                // 生成抢单信息对象
                UserRecode userRecode = new UserRecode();
                userRecode.setSkuId(skuId);
                userRecode.setUserId(userId);

                // 发送消息排队
                this.rabbitService.sendMessage(
                    MqConst.EXCHANGE_DIRECT_SECKILL_USER,
                    MqConst.ROUTING_SECKILL_USER, userRecode
                );

                return Result.ok();

            }


        }


        return Result.build(null, ResultCodeEnum.SECKILL_ILLEGAL);
    }

    /**
     * /api/activity/seckill/auth/getSeckillSkuIdStr/{skuId}
     * 获取抢单码
     *
     * @return
     */
    @GetMapping("/auth/getSeckillSkuIdStr/{skuId}")
    public Result getSeckillSkuIdStr(@PathVariable Long skuId, HttpServletRequest request) {

        // 获取用户id
        String userId = AuthContextHolder.getUserId(request);

        SeckillGoods seckillGoods = (SeckillGoods) this.redisTemplate.boundHashOps(RedisConst.SECKILL_GOODS).get(skuId.toString());

        // 判断
        if (seckillGoods != null) {
            // 获取当前时间
            Date curDate = new Date();
            // 判断
            if (DateUtil.dateCompare(seckillGoods.getStartTime(), curDate) && DateUtil.dateCompare(curDate, seckillGoods.getEndTime())) {

                // 生成抢单码
                String encrypt = MD5Util.encrypt(userId);

                return Result.ok(encrypt);


            }


        }


        return Result.fail().message("获取抢单码失败！");
    }


    /**
     * api/activity/seckill/findAll
     * 查询秒杀列表
     *
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<SeckillGoods> seckillGoodsList = seckillGoodsService.findAll();

        return Result.ok(seckillGoodsList);
    }


    /**
     * /api/activity/seckill/getSeckillGoods/{skuId}
     * 获取秒杀商品详情
     *
     * @param skuId
     * @return
     */
    @GetMapping("/getSeckillGoods/{skuId}")
    public Result getSeckillGoods(@PathVariable Long skuId) {
        SeckillGoods seckillGoods = seckillGoodsService.getSeckillGoods(skuId);

        return Result.ok(seckillGoods);
    }
}
