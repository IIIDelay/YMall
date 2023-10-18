/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import cn.hutool.core.lang.Assert;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.units.qual.C;
import org.iiidev.ymall.execption.ServiceRuntimeException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * NumHelper
 *
 * @Author IIIDelay
 * @Date 2023/10/18 19:27
 **/
public class NumHelper {
    public enum ComparisonEnum {
        LT,LTE,GT,GTE,EQ;
    }

    /**
     * 取整与小数
     *
     * @param bigDecimal bigDecimal
     * @return Pair<Integer, BigDecimal>
     */
    public static Pair<Integer, BigDecimal> floorCeiling(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }

        // 整数
        int intLeft = bigDecimal.intValue();
        // 小数
        BigDecimal floatRight = bigDecimal.subtract(BigDecimal.valueOf(intLeft));
        return Pair.of(intLeft, floatRight);
    }

    /**
     * 取整与小数
     *
     * @param doubleVal doubleVal
     * @return Pair<Integer, Double>
     */
    public static Pair<Integer, Double> floorCeiling(Double doubleVal) {
        if (doubleVal == null) {
            return null;
        }

        int intLeft = doubleVal.intValue();
        double floatRight = doubleVal - intLeft;

        return Pair.of(intLeft, floatRight);
    }


    /**
     * 计算小数位梯度
     *
     * @param input        input
     * @param gradientVal  gradientVal
     * @param roundingMode roundingMode
     */
    public static BigDecimal calcDecimalPlaceGradient(BigDecimal input, BigDecimal gradientVal, RoundingMode roundingMode) {
        String plainString = gradientVal.toPlainString();
        String floatString = StringUtils.substringAfterLast(plainString, ".");
        Pair<Integer, BigDecimal> flooredCeilingPair = floorCeiling(input);

        BigDecimal floatNum = new BigDecimal(StringUtils.join("0.", floatString));

        // 相等返回input
        if (eq(flooredCeilingPair.getRight(), floatNum)) {
            return input;
        }

        // 向上取整, 看梯度值 gradientVal (0.5, 005)
        if (RoundingMode.HALF_UP.equals(roundingMode)) {
            if (gt(flooredCeilingPair.getRight(), gradientVal)) {

            }
        } else if (RoundingMode.HALF_DOWN.equals(roundingMode)) {
            // 向上取整, 看梯度值 gradientVal (0.5, 005)

        }
        // 兜底input
        return input;
    }

    public static <IN extends Number> boolean lt(IN num1, IN num2){
        return compare(num1, num2, ComparisonEnum.LT);
    }

    public static <IN extends Number> boolean lte(IN num1, IN num2){
        return compare(num1, num2, ComparisonEnum.LTE);
    }

    public static <IN extends Number> boolean gte(IN num1, IN num2){
        return compare(num1, num2, ComparisonEnum.GTE);
    }

    public static <IN extends Number> boolean gt(IN num1, IN num2){
        return compare(num1, num2, ComparisonEnum.GT);
    }

    public static <IN extends Number> boolean eq(IN num1, IN num2){
        return compare(num1, num2, ComparisonEnum.EQ);
    }

    private static <IN extends Number> boolean compare(IN num1, IN num2, ComparisonEnum cenum) {
        Assert.notNull(num1, () -> new RuntimeException("num1 not empty"));
        Assert.notNull(num2, () -> new RuntimeException("num2 not empty"));
        int compareTo;
        if (num1 instanceof BigDecimal) {
            BigDecimal num1Temp = (BigDecimal) num1;
            BigDecimal num2Temp = (BigDecimal) num2;
            compareTo = num1Temp.compareTo(num2Temp);
        } else if (num1 instanceof Integer) {
            Integer num1Temp = (Integer) num1;
            Integer num2Temp = (Integer) num2;
            compareTo = num1Temp.compareTo(num2Temp);
        } else if (num1 instanceof Double) {
            Double num1Temp = (Double) num1;
            Double num2Temp = (Double) num2;
            compareTo = num1Temp.compareTo(num2Temp);
        } else {
            throw new RuntimeException("num must be instanceof BigDecimal | Integer | Double");
        }
        switch (cenum) {
            case GT:
                if (compareTo == 1) {
                    return true;
                }
                return false;
            case GTE:
                if (compareTo == 1 || compareTo == 0) {
                    return true;
                }
                return false;
            case EQ:
                if (compareTo == 0) {
                    return true;
                }
                return false;
            case LT:
                if (compareTo == -1) {
                    return true;
                }
                return false;
            case LTE:
                if (compareTo == -1 || compareTo == 0) {
                    return true;
                }
                return false;
        }
        throw new RuntimeException("cenum not empty.");
    }


}
