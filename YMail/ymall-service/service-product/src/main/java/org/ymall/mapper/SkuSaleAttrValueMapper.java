/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ymall.entity.SkuSaleAttrValue;

import java.util.List;
import java.util.Map;

public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValue> {
    /**
     * selectSaleAttrValuesBySpu
     *
     * @param spuId spuId
     * @return List<Map>
     */
    // 根据spuId 查询map 集合数据
    List<Map> selectSaleAttrValuesBySpu(Long spuId);
}
