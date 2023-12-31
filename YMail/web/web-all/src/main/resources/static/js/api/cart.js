/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

var cart = {

    api_name: '/api/cart',

  // 添加购物车
  addToCart(skuId, skuNum) {
    return request({
      url: this.api_name + '/addToCart/' + skuId + '/' + skuNum,
      method: 'get'
    })
  },

  // 我的购物车
  cartList() {
    return request({
      url: this.api_name + '/cartList',
      method: 'get'
    })
  },

  // 更新选中状态
  checkCart(skuId, isChecked) {
    return request({
      url: this.api_name + '/checkCart/' + skuId + '/' + isChecked,
      method: 'get'
    })
  },

// 刪除
  deleteCart(skuId) {
    return request({
      url: this.api_name + '/deleteCart/' + skuId,
      method: 'delete'
    })
  }
}
