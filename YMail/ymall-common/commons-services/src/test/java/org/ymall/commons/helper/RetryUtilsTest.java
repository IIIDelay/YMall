/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RetryUtilsTest
 *
 * @Author IIIDelay
 * @Date 2023/7/29 21:56
 **/
public class RetryUtilsTest {

    @Test
    public void retry() {
        AtomicInteger count1 = new AtomicInteger();
        RetryUtils.retry(3, ()->{
            int i = 1 / 1;
            count1.incrementAndGet();
            return Optional.empty();
        }, 100);

        AtomicInteger count2 = new AtomicInteger();
        RetryUtils.retry(3, ()->{
            count2.incrementAndGet();
            System.out.println("count2 = " + count2);
            int i = 1 / 0;
            return null;
        }, 100);

        Assertions.assertEquals(1, count1.get());
        Assertions.assertEquals(3, count2.get());
    }
}