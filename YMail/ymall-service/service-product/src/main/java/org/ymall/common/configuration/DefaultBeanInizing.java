/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.common.configuration;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.ymall.common.configuration.manger.ConfigManger;
import org.ymall.commons.config.MinioConfig;
import org.ymall.commons.config.RedisConfig;
import org.ymall.commons.context.ApplicationBeanContext;

import javax.sql.DataSource;

@Configuration
@Import({MybatisPlusConfig.class, RedisConfig.class})
@EnableConfigurationProperties(MinioConfig.class)
@EnableAspectJAutoProxy
public class DefaultBeanInizing {
    @Bean
    public DataSource dataSource(ConfigManger cm) {
        return cm.getConfigProperties().mysql();
    }

    /**
     * beanContext : 静态工具类注入IOC
     *
     * @return ApplicationBeanContext
     */
    @Bean
    public ApplicationBeanContext beanContext() {
        return new ApplicationBeanContext();
    }
}
