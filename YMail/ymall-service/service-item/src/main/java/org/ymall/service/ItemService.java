/*
 * Copyright (c) 2023, author: IIIDev
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
    public Map<String, Object> getItemBySkuId(Long skuId);
}
