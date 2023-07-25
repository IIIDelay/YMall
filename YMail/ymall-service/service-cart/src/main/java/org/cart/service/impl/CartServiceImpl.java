/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.cart.service.impl;

import common.RedisConstants;
import org.cart.service.CartService;
import org.iiidev.ymall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.ymall.model.cart.CartInfo;
import org.ymall.model.product.SkuInfo;
import utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("all")
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 加入购物车
     *
     * @param skuId
     * @param skuNum
     * @param userId 存储数据：
     *               1.区别用户
     *               2.区别商品
     *               <p>
     *               Hash
     *               <p>
     *               key:用户id
     *               filed: 商品id
     *               vlaue:商品对象信息
     *               <p>
     *               user:1:cart   22    CartInfo
     *               <p>
     *               指令：HSET key field value
     *               <p>
     *               思路：
     *               1.先根据用户id获取购物车列表
     *               2.判读是否存在当前添加的商品
     *               存在：数量相加
     *               不存在： 新建添加
     */
    @Override
    public void addToCart(Long skuId, Integer skuNum, String userId) {

        // 定义key
        String cartKey = this.getKey(userId);
        // 获取购物车列表 String == user:1:cart = ,String == 22==,  CartInfo
        BoundHashOperations<String, String, CartInfo> boundHashOps = redisTemplate.boundHashOps(cartKey);
        // 定义购车详情信息对象
        CartInfo cartInfo = null;

        // 判断当前列表是否包含sku
        if (boundHashOps.hasKey(skuId.toString())) {
            // 存在
            cartInfo = boundHashOps.get(skuId.toString());
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            cartInfo.setUpdateTime(new Date());
            cartInfo.setSkuPrice(productFeignClient.getSkuPrice(skuId));


        } else {
            // 不存在
            cartInfo = new CartInfo();
            cartInfo.setUserId(userId);
            cartInfo.setSkuId(skuId);
            // 远程请求sku详情数据
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);

            cartInfo.setCartPrice(skuInfo.getPrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setImgUrl(skuInfo.getSkuDefaultImg());
            cartInfo.setSkuName(skuInfo.getSkuName());
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
            // 实时价格
            cartInfo.setSkuPrice(skuInfo.getPrice());


        }
        // 存储
        boundHashOps.put(skuId.toString(), cartInfo);


    }

    /**
     * 展示购物车--合并购物车
     *
     * @param userId
     * @param userTempId
     * @return 1.未登录的购物车
     * <p>
     * 2.登录后的购物车-合并
     */
    @Override
    public List<CartInfo> cartList(String userId, String userTempId) {

        // 定义未登录购物车
        List<CartInfo> nologinCartList = null;
        // 获取未登录数据
        if (!StringUtils.isEmpty(userTempId)) {

            nologinCartList = redisTemplate.boundHashOps(this.getKey(userTempId)).values();


        }
        // 表示未登录
        if (StringUtils.isEmpty(userId) && !CollectionUtils.isEmpty(nologinCartList)) {

            nologinCartList.sort((o1, o2) -> {

                return DateUtil.truncatedCompareTo(o2.getUpdateTime(), o1.getUpdateTime(), Calendar.SECOND);
            });

            return nologinCartList;

        }

        // 登录后的合并

        List<CartInfo> loginCartList = null;

        // 获取登录后的数据
        BoundHashOperations<String, String, CartInfo> boundHashOps = redisTemplate.boundHashOps(this.getKey(userId));
        // 判断是否为空
        if (!CollectionUtils.isEmpty(nologinCartList)) {
            // 合并
            nologinCartList.forEach(cartInfo -> {

                // 判断登录后的集合中是否包含
                if (boundHashOps.hasKey(cartInfo.getSkuId().toString())) {
                    // 已经在登录的购物车列表中存在 --数量更改
                    CartInfo loginCartInfo = boundHashOps.get(cartInfo.getSkuId().toString());
                    // 更新数量
                    loginCartInfo.setSkuNum(loginCartInfo.getSkuNum() + cartInfo.getSkuNum());
                    // 更新时间
                    loginCartInfo.setUpdateTime(new Date());
                    // 选中状态
                    if (cartInfo.getIsChecked().intValue() == 1) {

                        loginCartInfo.setIsChecked(1);
                    }


                    boundHashOps.put(cartInfo.getSkuId().toString(), loginCartInfo);
                } else {
                    // 添加到登录后的购物车列表

                    // 添加userId
                    cartInfo.setUserId(userId);
                    // 更新时间
                    cartInfo.setUpdateTime(new Date());

                    boundHashOps.put(cartInfo.getSkuId().toString(), cartInfo);


                }


            });

            // 未登录的购物车数据--清除
            redisTemplate.delete(getKey(userTempId));


        }

        // 获取登录后的购物车列表
        loginCartList = boundHashOps.values();
        if (CollectionUtils.isEmpty(loginCartList)) {

            loginCartList = new ArrayList<>();
        }
        // 排序
        if (!CollectionUtils.isEmpty(loginCartList)) {

            loginCartList.sort((o1, o2) -> {

                return DateUtil.truncatedCompareTo(o2.getUpdateTime(), o1.getUpdateTime(), Calendar.SECOND);
            });


        }


        return loginCartList;

//        List<CartInfo> cartInfoList=null;
//
//
//
//        //判断临时id是否为空
//        if(!StringUtils.isEmpty(userTempId)){
//
//            //获取key
//            String cartKey = this.getKey(userTempId);
//            //取值
//            cartInfoList=redisTemplate.boundHashOps(cartKey).values();
//        }
//
//        //判断用户id是否为空
//        if(!StringUtils.isEmpty(userId)){
//
//            //获取key
//            String cartKey = this.getKey(userId);
//            //取值
//            cartInfoList=redisTemplate.boundHashOps(cartKey).values();
//        }
//        //排序updateTime
//        if(!CollectionUtils.isEmpty(cartInfoList)){
//            cartInfoList.sort((o1,o2)->{
//
//
//                return DateUtil.truncatedCompareTo(o2.getUpdateTime(),o1.getUpdateTime(), Calendar.SECOND);
//            });
//
//
//        }
//
//


//        return null;
    }

    /**
     * 更改选中状态
     *
     * @param userId
     * @param skuId
     * @param isChecked
     */
    @Override
    public void checkCart(String userId, Long skuId, Integer isChecked) {

        // 获取数据列表
        BoundHashOperations<String, String, CartInfo> boundHashOps = redisTemplate.boundHashOps(this.getKey(userId));
        // 判断skuid是否存在
        if (boundHashOps.hasKey(skuId.toString())) {
            CartInfo cartInfo = boundHashOps.get(skuId.toString());
            // 修改状态
            cartInfo.setIsChecked(isChecked);
            // 更新
            boundHashOps.put(skuId.toString(), cartInfo);


        }

    }

    /**
     * 删除购物车
     *
     * @param userId
     * @param skuId
     */
    @Override
    public void deleteCart(String userId, Long skuId) {

        // 获取数据列表
        redisTemplate.boundHashOps(this.getKey(userId)).delete(skuId.toString());


    }

    /**
     * 获取选中状态的购物车列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<CartInfo> getCartCheckedList(String userId) {
        // 定义变量接收结果
        List<CartInfo> cartInfos = null;

        // 获取所有数据
        BoundHashOperations<String, String, CartInfo> boundHashOps = redisTemplate.boundHashOps(this.getKey(userId));
        List<CartInfo> cartInfoList = boundHashOps.values();
        // 判断处理
        if (!CollectionUtils.isEmpty(cartInfoList)) {
            cartInfos = cartInfoList.stream().filter(item -> {
                // item表示购物车对象

                // 根据价格
                item.setSkuPrice(productFeignClient.getSkuPrice(item.getSkuId()));


                // 判断是否选中

//                return item.getIsChecked().intValue()==1;
                return "1".equals(String.valueOf(item.getIsChecked()));

            }).collect(Collectors.toList());


        }
        return cartInfos;
    }

    /**
     * 获取操作购物车的key
     *
     * @param userId
     * @return user:用户id:cart
     */
    private String getKey(String userId) {
        return RedisConstants.USER_KEY_PREFIX + userId + RedisConstants.USER_CART_KEY_SUFFIX;
    }


    @Override
    public List<CartInfo> getCartList(String userId, String userTempId) {
        //获取临时用户购物车数据
        List<CartInfo> cartInfoList = null;
        if(!StringUtils.isEmpty(userTempId)){
            BoundHashOperations<String, String, CartInfo> boundHashOps = this.redisTemplate.boundHashOps(this.getCartKey(userTempId));
            cartInfoList = boundHashOps.values();
        }

        //获取用户购物车数据
        if(!StringUtils.isEmpty(userId)){
            BoundHashOperations<String, String, CartInfo> boundHashOps = this.redisTemplate.boundHashOps(this.getCartKey(userId));
            cartInfoList = boundHashOps.values();
        }

        if(!CollectionUtils.isEmpty(cartInfoList)){
            //  展示购物车列表的时候应该有顺序！ 京东：按照更新时间！ 苏宁：创建时间！
            cartInfoList.sort((o1,o2)->{
                //  使用时间进行比较
                return DateUtil.truncatedCompareTo(o2.getUpdateTime(),o1.getUpdateTime(), Calendar.SECOND);
            });
        }
        return cartInfoList;
    }

}
