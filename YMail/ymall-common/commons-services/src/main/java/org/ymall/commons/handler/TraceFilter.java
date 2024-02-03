/*
 * Copyright (c) 2024. 版权归III_Delay所有
 */

package org.ymall.commons.handler;

import org.iiidev.common.utils.mdc.MDCUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        MDCUtils.generateTraceIdAndFill();
        // 继续过滤
        chain.doFilter(request, response);
        MDCUtils.clearTrace();
    }

}