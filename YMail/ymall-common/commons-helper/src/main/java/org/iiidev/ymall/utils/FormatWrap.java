/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.text.Format;

/**
 * FormatWrap
 *
 * @Author IIIDelay
 * @Date 2023/8/17 23:27
 **/
public class FormatWrap<IN> {
    private final Format format;
    private IN in;

    public static <IN> void nonOf(IN in) {
        if (in == null) {
            return;
        }
        FormatWrap<IN> wrap = new FormatWrap<>();

    }

    private FormatWrap(Format format, IN in) {
        this.format = format;
        this.in = in;
    }

    private FormatWrap() {
        this.format = null;
    }
}
