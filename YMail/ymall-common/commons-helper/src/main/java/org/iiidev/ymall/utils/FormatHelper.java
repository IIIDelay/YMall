/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

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
     * @param patten       patten
     * @param scale        scale
     * @param roundingMode roundingMode
     * @return String
     */
    public static <IN extends Number> String format(IN in, String patten, int scale, RoundingMode roundingMode) {
        DecimalFormat format = new DecimalFormat(patten);
        format.setRoundingMode(roundingMode);
        return format.format(in);
    }
}
