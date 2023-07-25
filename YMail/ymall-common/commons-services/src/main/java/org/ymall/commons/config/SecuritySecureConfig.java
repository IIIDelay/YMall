/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * SecuritySecureConfig
 *
 * @Author IIIDelay
 * @Date 2023/7/23 19:30
 **/
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录成功处理类
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");

        http.authorizeRequests()
            // 静态文件允许访问
            .antMatchers().permitAll()
            // 放行
            .antMatchers("/**").permitAll()
            // 其他所有请求需要登录, anyRequest 不能配置在 antMatchers 前面
            .anyRequest().authenticated()
            .and()
            // 登录页面配置，用于替换security默认页面
            .formLogin().loginPage("/login").successHandler(successHandler).and()
            // 登出页面配置，用于替换security默认页面
            .logout().logoutUrl("/logout").and()
            .httpBasic().and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringAntMatchers(
                "/instances",
                "/actuator/**"
            );
    }
}
