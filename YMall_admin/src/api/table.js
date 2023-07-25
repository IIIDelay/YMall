/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/table/list',
    method: 'get',
    params
  })
}
