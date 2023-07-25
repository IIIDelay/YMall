/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

var orderInfo = {

    api_name: '/api/order',

    getOrderDetail(orderDetailId) {
        return request({
            url: this.api_name + `/auth/getOrderDetail/${orderDetailId}`,
            method: 'get'
        })
    }
}
