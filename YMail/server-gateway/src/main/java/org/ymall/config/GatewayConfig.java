/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * GatewayConfig
 *
 * @author IIIDelay
 * @createTime 2023年03月03日 16:12:00
 */
@Configuration
public class GatewayConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        // cors跨域配置对象
        CorsConfiguration configuration = new CorsConfiguration();
        // 跨域配置报错，将.allowedOrigins替换成.addAllowedOriginPattern 即可
        configuration.addAllowedOriginPattern("*"); //设置允许访问的网络
        configuration.setAllowCredentials(true); // 设置是否从服务器获取cookie
        configuration.addAllowedMethod("*"); // 设置请求方法 * 表示任意
        configuration.addAllowedHeader("*"); // 所有请求头信息 * 表示任意

        // 配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", configuration);
        // cors过滤器对象
        return new CorsWebFilter(configurationSource);

    }


    // @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("service-product", r -> r.path("/*/product/**")
                        .uri("http://localhost:9901"))
                .build();
    }
}
