/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package dto;

import Annotation.JcBigDecConvert;
import common.UnitConvertType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MyYearSumReportDTO implements Serializable {
    private static final long serialVersionUID = 5285987517581372888L;

    // 支付总金额
    @JcBigDecConvert(name = UnitConvertType.B)
    private BigDecimal payTotalAmount;

    // jc金额百分比
    @JcBigDecConvert(name = UnitConvertType.PERCENTAGE)
    private BigDecimal jcAmountPercentage;

    // jc计数千分比
    @JcBigDecConvert(name = UnitConvertType.PERMIL)
    private BigDecimal jcCountPermillage;

    // 保留2位
    @JcBigDecConvert(name = UnitConvertType.R)
    private BigDecimal length;

    // 保留2位
    @JcBigDecConvert(name = UnitConvertType.R)
    private BigDecimal width;

}