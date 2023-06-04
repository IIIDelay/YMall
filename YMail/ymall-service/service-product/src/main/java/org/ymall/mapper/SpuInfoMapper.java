/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ymall.entity.SkuSpuInfo;
import org.ymall.entity.SpuInfo;

import java.util.List;

public interface SpuInfoMapper extends BaseMapper<SpuInfo> {

    int updateTime();

    List<SpuInfo> list();

    List<SkuSpuInfo> page();

    List<SkuSpuInfo> listpage();
}
