/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

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
     * @param runnable         runnable
     * @param intervalMillTime intervalMillTime
     */
    public void retry(int count, Runnable runnable, int intervalMillTime) {
        int num = count;
        try {
            while (num <= count) {
                runnable.run();
            }
        } catch (Exception e) {
            try {
                num--;
                log.warn("逻辑重试中, 次数: {}", num);
                TimeUnit.MILLISECONDS.sleep(intervalMillTime);
                if (num <= 0) {
                    throw new RuntimeException(e);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
