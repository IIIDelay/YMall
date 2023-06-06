package org.cart.service;

import org.ymall.model.cart.CartInfo;

import java.util.List;

public interface CartService {
    /**
     * 加入购物车
     * @param skuId
     * @param skuNum
     * @param userId
     */
    void addToCart(Long skuId, Integer skuNum, String userId);

    /**
     * 展示购物车
     * @param userId
     * @param userTempId
     * @return
     */
    List<CartInfo> cartList(String userId, String userTempId);

    /**
     * 更改选中状态
     * @param userId
     * @param skuId
     * @param isChecked
     */
    void checkCart(String userId, Long skuId, Integer isChecked);

    /**
     * 删除购物车
     * @param userId
     * @param skuId
     */
    void deleteCart(String userId, Long skuId);

    /**
     * 获取选中状态的购物车列表
     * @param userId
     * @return
     */
    List<CartInfo> getCartCheckedList(String userId);

    /**
     * 通过用户Id 查询购物车列表
     * @param userId
     * @param userTempId
     * @return
     */
    List<CartInfo> getCartList(String userId, String userTempId);

}
