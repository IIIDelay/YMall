/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.context;

import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.AsyncContext;
import java.io.Serializable;

/**
 * HttpRequestHolder
 *
 * @Author IIIDelay
 * @Date 2023/11/16 21:37
 **/
public class HttpRequestHolder  {
    /**
     * asyncServletRequest : 异步线程获取httprequest
     */
    public void asyncServletRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        AsyncContext asyncContext = attributes.getRequest().getAsyncContext();
        RequestContextHolder.setRequestAttributes(attributes, true);
    }
}