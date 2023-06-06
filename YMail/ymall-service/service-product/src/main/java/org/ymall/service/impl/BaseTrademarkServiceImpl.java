/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.ymall.mapper.BaseTrademarkMapper;
import org.ymall.model.product.BaseTrademark;
import org.ymall.service.IBaseTrademarkService;

@Service
public class BaseTrademarkServiceImpl extends ServiceImpl<BaseTrademarkMapper, BaseTrademark> implements IBaseTrademarkService {

   @Autowired
   private BaseTrademarkMapper baseTrademarkMapper;

   @Override
   public IPage<BaseTrademark> getPage(Page<BaseTrademark> pageParam) {
      QueryWrapper<BaseTrademark> queryWrapper = new QueryWrapper<>();
      queryWrapper.orderByAsc("id");

      IPage<BaseTrademark> page = baseTrademarkMapper.selectPage(pageParam, queryWrapper);
      return page;
   }
}
