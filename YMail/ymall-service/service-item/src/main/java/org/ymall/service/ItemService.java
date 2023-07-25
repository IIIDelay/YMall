/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.service;

import java.util.Map;

public interface ItemService {
    /**
     * getItemBySkuId
     *
     * @param skuId skuId
     * @return Map<String, Object>
     */
    Map<String, Object> getItemBySkuId(Long skuId);
}
