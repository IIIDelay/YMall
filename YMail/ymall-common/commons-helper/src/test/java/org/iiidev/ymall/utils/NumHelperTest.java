/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NumHelperTest
 *
 * @Author IIIDelay
 * @Date 2023/10/18 20:31
 **/
public class NumHelperTest {
    @Test
    public void sqlTest() {
        String sql = "select  from dual";
    }

    @Test
    public void lt() {
        boolean eq = NumHelper.eq(BigDecimal.valueOf(1.1), BigDecimal.valueOf(1.1));
        boolean eq1 = NumHelper.eq(BigDecimal.valueOf(1.101), BigDecimal.valueOf(1.1));
        System.out.println("eq = " + eq);
        System.out.println("eq1 = " + eq1);

        boolean gt = NumHelper.gt(BigDecimal.valueOf(1.101), BigDecimal.valueOf(1.1));
        boolean gte = NumHelper.gte(BigDecimal.valueOf(1.101), BigDecimal.valueOf(1.1));
        System.out.println("gt = " + gt);
        System.out.println("gte = " + gte);

        boolean lt = NumHelper.lt(BigDecimal.valueOf(1.101), BigDecimal.valueOf(1.1));
        boolean lte = NumHelper.lte(BigDecimal.valueOf(1.101), null);
        System.out.println("lt = " + lt);
        System.out.println("lte = " + lte);
    }
}