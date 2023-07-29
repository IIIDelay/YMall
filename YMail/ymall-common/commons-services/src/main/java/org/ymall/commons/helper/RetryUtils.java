/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * RetryUtils
 *
 * @Author IIIDelay
 * @Date 2023/7/29 0:13
 **/
@Slf4j
public class RetryUtils {

    /**
     * retry
     *
     * @param count            count
     * @param supplier         supplier
     * @param intervalMillTime intervalMillTime
     * @return OUT
     */
    public static <OUT> OUT retry(int count, Supplier<OUT> supplier, int intervalMillTime) {
        OUT out = null;
        int num = 0;
        while (num < count) {
            try {
                out = supplier.get();
                break;
            } catch (Exception e) {
                try {
                    ++num;
                    System.out.println("num = " + num);
                    TimeUnit.MILLISECONDS.sleep(intervalMillTime);
                    if (num >= count) {
                        throw new RuntimeException(e);
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return out;
    }
}
