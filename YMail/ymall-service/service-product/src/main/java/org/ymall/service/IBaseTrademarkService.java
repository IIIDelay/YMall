/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.ymall.model.product.BaseTrademark;

public interface IBaseTrademarkService extends IService<BaseTrademark> {

   /**
    * Banner分页列表
    * @param pageParam
    * @return
    */
   IPage<BaseTrademark> getPage(Page<BaseTrademark> pageParam);

}
