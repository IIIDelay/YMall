/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.item.client;

import org.iiidev.ymall.item.client.impl.ItemDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.iiidev.ymall.result.ServiceResponse;

@FeignClient(value = "service-item", fallback = ItemDegradeFeignClient.class)
public interface ItemFeignClient {

    /**
     * @param skuId
     * @return
     */
    @GetMapping("/api/item/{skuId}")
    ServiceResponse getItem(@PathVariable("skuId") Long skuId);

}
