/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.ymall.model.payment.PaymentInfo;
import org.ymall.payment.client.impl.PaymentDegradeFeignClient;

@FeignClient(value = "service-payment", fallback = PaymentDegradeFeignClient.class)
public interface PaymentFeignClient {


    /**
     * 获取支付信息
     *
     * @param outTradeNo
     * @return
     */
    @GetMapping("/api/payment/alipay/getPaymentInfo/{outTradeNo}")
    PaymentInfo getPaymentInfo(@PathVariable String outTradeNo);


    /**
     * 查询订单记录
     * http://localhost:8205/api/payment/alipay/checkPayment/30
     *
     * @param orderId
     * @return
     */
    @GetMapping("/api/payment/alipay/checkPayment/{orderId}")
    Boolean checkPayment(@PathVariable Long orderId);

    /**
     * http://localhost:8205/api/payment/alipay/closePay/25
     * 根据订单id关闭订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/api/payment/alipay/closePay/{orderId}")
    Boolean closePay(@PathVariable Long orderId);
}
