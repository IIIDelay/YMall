/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.ymall.entity.BaseTrademark;

public interface IBaseTrademarkService extends IService<BaseTrademark> {

   /**
    * Banner分页列表
    * @param pageParam
    * @return
    */
   IPage<BaseTrademark> getPage(Page<BaseTrademark> pageParam);

}
