/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * FormatHelper
 *
 * @Author IIIDelay
 * @Date 2023/8/17 23:38
 **/
public class FormatHelper {
    /**
     * format
     *
     * @param in           in
     * @param patten       pattenr
     * @param roundingMode roundingMode
     * @return String
     */
    public static <IN extends Number> String format(IN in, String patten, RoundingMode roundingMode) {
        DecimalFormat format = new DecimalFormat(patten);
        format.setRoundingMode(roundingMode);
        return format.format(in);
    }

    public static void main(String[] args) {
        String format = format(BigDecimal.valueOf(22.232222822), "0.0000%", RoundingMode.HALF_UP);
        System.out.println("format = " + format);
    }
}
