/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

import request from '@/utils/request'

const api_name = '/admin/order'

export default {

  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj // url查询字符串或表单键值对
    })
  },

  show(orderId) {
    return request({
      url: `${api_name}/show/${orderId}`,
      method: 'get'
    })
  },

  recieve(id) {
    return request({
      url: `${api_name}/recieve/${id}`,
      method: 'get'
    })
  }
}
