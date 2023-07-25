/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

var list = {

    api_name: '/api/list',

  // 搜索
  getPageList(searchObj) {
    return request({
      url: this.api_name + '/search',
      method: 'post',
      data: searchObj // url查询字符串或表单键值对
    })
  },
}
