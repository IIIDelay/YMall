/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.ware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.ymall.ware.bean.WareSku;

import java.util.List;

/**
 * @param
 * @return
 */
@Repository
public interface WareSkuMapper extends BaseMapper<WareSku> {

    public Integer selectStockBySkuid(String skuid);

    public int incrStockLocked(WareSku wareSku);

    public int selectStockBySkuidForUpdate(WareSku wareSku);

    public int  deliveryStock(WareSku wareSku);

    public List<WareSku> selectWareSkuAll();
}
