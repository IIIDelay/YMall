/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

import request from '@/utils/request'

const api_name = '/admin/user'

export default {

  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },

  lock(id, status) {
    return request({
      url: `${api_name}/lock/${id}/${status}`,
      method: 'get'
    })
  },

  show(id) {
    return request({
      url: `${api_name}/show/${id}`,
      method: 'get'
    })
  }
}
