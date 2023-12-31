/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * getPasswordErrorLockTime
     *
     * @param time time
     * @return LocalDateTime
     */
    public static LocalDateTime getPasswordErrorLockTime(String time) {
        if (time == null || "".equals(time)) {
            return LocalDateTime.MAX;
        }
        if ("0".equals(time)) {
            return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        }
        char unit = Character.toLowerCase(time.charAt(time.length() - 1));

        if (time.length() == 1) {
            unit = 'd';
        }
        Long lastTime = NumberHelper.longValueOf0(time.substring(0, time.length() - 1));

        LocalDateTime passwordErrorLastTime = LocalDateTime.MAX;
        switch (unit) {
            // 时
            case 'h':
                passwordErrorLastTime = LocalDateTime.now().plusHours(lastTime);
                break;
            // 天
            case 'd':
                passwordErrorLastTime = LocalDateTime.now().plusDays(lastTime);
                break;
            // 周
            case 'w':
                passwordErrorLastTime = LocalDateTime.now().plusWeeks(lastTime);
                break;
            // 月
            case 'm':
                passwordErrorLastTime = LocalDateTime.now().plusMonths(lastTime);
                break;
            default:
                passwordErrorLastTime = LocalDateTime.now().plusDays(lastTime);
                break;
        }

        return passwordErrorLastTime;
    }

}


