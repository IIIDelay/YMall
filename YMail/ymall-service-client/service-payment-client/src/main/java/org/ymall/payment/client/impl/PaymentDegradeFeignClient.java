/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.payment.client.impl;

import org.springframework.stereotype.Component;
import org.ymall.model.payment.PaymentInfo;
import org.ymall.payment.client.PaymentFeignClient;

@Component
public class PaymentDegradeFeignClient implements PaymentFeignClient {
    @Override
    public PaymentInfo getPaymentInfo(String outTradeNo) {
        return null;
    }

    @Override
    public Boolean checkPayment(Long orderId) {
        return null;
    }

    @Override
    public Boolean closePay(Long orderId) {
        return null;
    }
}
