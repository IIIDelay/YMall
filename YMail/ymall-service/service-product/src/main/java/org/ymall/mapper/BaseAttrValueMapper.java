/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.ymall.model.product.BaseAttrValue;

import java.util.List;
import java.util.Map;

/**
 * 属性值表(BaseAttrValue)表数据库访问层
 *
 * @author IIIDev
 * @since 2023-03-02 22:02:27
 */
public interface BaseAttrValueMapper extends BaseMapper<BaseAttrValue> {
    List<BaseAttrValue> queryBaseAttrValueList(@Param("map") Map<String, Long> map,
                                               @Param("baseAttrValues") List<BaseAttrValue> baseAttrValues);
}

