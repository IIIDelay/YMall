/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.util;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * TimeTest
 *
 * @Author IIIDelay
 * @Date 2023/11/4 0:39
 **/
public class TimeTest {
    @Test
    public void name() throws InterruptedException {
        int count = 0;
        while (true) {
            count++;
            System.out.println("ni");
            TimeUnit.MILLISECONDS.sleep(RandomUtil.randomLong(200, 800));
            System.out.println(count);
            if (count > 1000000) {
                count = 0;
            }
        }
    }
}
