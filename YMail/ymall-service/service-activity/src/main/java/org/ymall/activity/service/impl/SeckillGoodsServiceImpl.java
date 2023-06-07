package org.ymall.activity.service.impl;

import common.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.ymall.activity.mapper.SeckillGoodsMapper;
import org.ymall.activity.service.SeckillGoodsService;
import org.ymall.activity.util.CacheHelper;
import org.ymall.model.activity.OrderRecode;
import org.ymall.model.activity.SeckillGoods;
import utils.MD5Util;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@SuppressWarnings("all")
public class SeckillGoodsServiceImpl implements SeckillGoodsService {



    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    /**
     * 查询秒杀列表
     * @return
     */
    @Override
    public List<SeckillGoods> findAll() {

        List<SeckillGoods> values = redisTemplate.opsForHash().values(RedisConstants.SECKILL_GOODS);

//         List<SeckillGoods> values= redisTemplate.boundHashOps(RedisConst.SECKILL_GOODS).values();
        return values;
    }

    /**
     * 获取商品详情
     * @param skuId
     * @return
     */
    @Override
    public SeckillGoods getSeckillGoods(Long skuId) {
        return (SeckillGoods) this.redisTemplate.boundHashOps(RedisConstants.SECKILL_GOODS).get(skuId.toString());
    }

    /**
     * 抢单
     * @param userId
     * @param skuId
     */
    @Override
    public void seckillOrder(String userId, Long skuId) {

        //校验状态位
        String stat = (String) CacheHelper.get(skuId.toString());
        if("0".equals(stat)){
            return ;
        }

        //校验是否下过单
        Boolean flag = this.redisTemplate.opsForValue().setIfAbsent(RedisConstants.SECKILL_USER + userId, skuId.toString());

        if(!flag){
            return;
        }


        //校验库存
        String stockSkuId = (String) this.redisTemplate.boundListOps(RedisConstants.SECKILL_STOCK_PREFIX + skuId).rightPop();
          if(StringUtils.isEmpty(stockSkuId))  {
              this.redisTemplate.convertAndSend("seckillPush",skuId+":0");
              return ;
          }



        //生成订单信息存储到redis
        OrderRecode orderRecode =new OrderRecode();
        orderRecode.setUserId(userId);
        orderRecode.setNum(1);
        SeckillGoods seckillGoods = this.getSeckillGoods(skuId);
        orderRecode.setSeckillGoods(seckillGoods);
        //订单码
        orderRecode.setOrderStr(MD5Util.encrypt(userId+skuId));

        //存储
        redisTemplate.boundHashOps(RedisConstants.SECKILL_ORDERS).put(userId,orderRecode);


        //更新库存量
        this.updateStockCount(skuId);

    }

    /**
     * 修改mysql 修改 redis
     * @param skuId
     */
    private void updateStockCount(Long skuId) {

        //获取锁对象
        Lock lock=new ReentrantLock();
        //加锁
        lock.lock();
        try {
            //获取库存
            Long stockKey = this.redisTemplate.boundListOps(RedisConstants.SECKILL_STOCK_PREFIX + skuId).size();


            //获取当前尚品的信息
            SeckillGoods seckillGoods = this.getSeckillGoods(skuId);
            //设置库存
            seckillGoods.setStockCount(stockKey.intValue());
            //修改数据库mysql
            this.seckillGoodsMapper.updateById(seckillGoods);

            //更新缓存
            this.redisTemplate.boundHashOps(RedisConstants.SECKILL_GOODS).put(skuId.toString(),seckillGoods);
        } finally {
            lock.unlock();
        }


    }
}
