/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.model.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.ymall.model.activity.CouponInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderTradeVo implements Serializable {
   
   private static final long serialVersionUID = 1L;

   //   送货清单
   @ApiModelProperty(value = "订单明细")
   private List<OrderDetailVo> orderDetailVoList;

   //   返现总金额
   @ApiModelProperty(value = "促销优惠金额")
   private BigDecimal activityReduceAmount;

   //  使用优惠/抵用
   @ApiModelProperty(value = "订单优惠券列表")
   private List<CouponInfo> couponInfoList;

}