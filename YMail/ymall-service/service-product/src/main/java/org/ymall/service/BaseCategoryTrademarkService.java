/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ymall.model.product.BaseCategoryTrademark;
import org.ymall.model.product.BaseTrademark;
import org.ymall.model.product.CategoryTrademarkVo;

import java.util.List;

public interface BaseCategoryTrademarkService extends IService<BaseCategoryTrademark> {

    /**
     * 根据三级分类获取品牌
     * @param category3Id
     * @return
     */
    List<BaseTrademark> findTrademarkList(Long category3Id);

    /**
     * 保存分类与品牌关联
     * @param categoryTrademarkVo
     */
    void save(CategoryTrademarkVo categoryTrademarkVo);

    /**
     * 获取当前未被三级分类关联的所有品牌
     * @param category3Id
     * @return
     */
    List<BaseTrademark> findCurrentTrademarkList(Long category3Id);

    /**
     * 删除关联
     *
     * @param category3Id category3Id
     * @param trademarkId trademarkId
     */
    void remove(Long category3Id, Long trademarkId);

    /**
     * removeBaseCategoryTrademarkById
     *
     * @param category3Id category3Id
     * @param trademarkId trademarkId
     */
    void removeBaseCategoryTrademarkById(Long category3Id, Long trademarkId);
}
