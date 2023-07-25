/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.ymall.config.CustomConfig;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * BlackListFilter
 *
 * @author IIIDelay
 * @createTime 2023年04月02日 00:03:00
 */
@Component
public class BlackListFilter implements GlobalFilter, Ordered {

    @Autowired
    private CustomConfig customConfig;

    /**
     * filter : 全局过滤器，会对所有路由生效
     *
     * @param exchange 封装request和response对象的上下文
     * @param chain    chain
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 思路: 获取客户端ip，判断是否在黑名单中，在的话就拒绝访问，不在就放行
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String clientIp = request.getRemoteAddress().getHostString();
        if (customConfig.getBlackList().contains(clientIp)) {
            // 拒绝访问，返回
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            String data = "Request be denied";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(wrap));
        }
        // 合法执行正常返回
        return chain.filter(exchange);
    }

    /**
     * getOrder : 数值越小，优先级越高
     *
     * @return int
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
