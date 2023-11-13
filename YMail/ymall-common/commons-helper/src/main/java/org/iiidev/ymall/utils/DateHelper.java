/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import cn.hutool.core.date.DateUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * DateHelper
 *
 * @Author IIIDelay
 * @Date 2023/11/12 22:01
 **/
public class DateHelper {
    public static void dateToLocalDateTime(Date date) {
    }

    public static Date LocalDateTimeToDate(LocalDateTime input) {
        Instant instant = input.now().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date beginOfDay(int offsetDay) {
        // Instant instant = LocalDateTime.of(LocalDate.now().minusDays(offsetDay), LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant();
        Instant instant = LocalDateTime.now().plusDays(offsetDay).with(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * beginOfDayEQ : 包含今日
     *
     * @param offsetDay offsetDay
     * @return Date
     */
    public static Date beginOfDayEQ(int offsetDay) {
        return beginOfDay(offsetDay + 1);
    }

    public static Date endOfDayEQ(int offsetDay) {
        return endOfDay(offsetDay + 1);
    }

    public static Date endOfDay(int offsetDay) {
        // Instant instant = LocalDateTime.of(LocalDate.now().minusDays(offsetDay), LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant();
        Instant instant = LocalDateTime.now().plusDays(offsetDay).with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date beginOfMonth(int offsetMonth) {
        Instant instant = LocalDateTime.now().plusMonths(offsetMonth).withDayOfMonth(1).with(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static void main(String[] args) {
        // Date date = beginOfDay(-1);
        // Date date1 = endOfDay(-1);
        // String format = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
        // String format1 = DateUtil.format(date1, "yyyy-MM-dd HH:mm:ss");
        // System.out.println("format = " + format);
        // System.out.println("format1 = " + format1);
        //
        // Instant instant = LocalDateTime.now().withDayOfMonth(1).with(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant();
        // System.out.println("Date.from(instant) = " + Date.from(instant));

        Date date = beginOfDay(0);
        String format = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
        System.out.println("format = " + format);
    }
}