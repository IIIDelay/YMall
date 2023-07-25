/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

var payment = {

    api_name: '/api/payment/weixin',

    createNative(orderId) {
        return request({
            url: this.api_name + `/createNative/` + orderId,
            method: 'get'
        })
    },

    queryPayStatus(orderId) {
        return request({
            url: this.api_name + `/queryPayStatus/` + orderId,
            method: 'get'
        })
    }
}
