package org.iiidev.ymall.item.client.impl;

import org.iiidev.ymall.item.client.ItemFeignClient;
import org.springframework.stereotype.Component;
import result.ServiceResponse;

@Component
public class ItemDegradeFeignClient implements ItemFeignClient {

    @Override
    public ServiceResponse<Void> getItem(Long skuId) {
        return ServiceResponse.fail();
    }
}
