package org.ymall.commons.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.ymall.commons.config.manger.CommonConfigManger;
import org.ymall.commons.context.ApplicationBeanContext;

import javax.sql.DataSource;

/**
 * DefaultBeanInitiating
 *
 * @Author IIIDelay
 * @Date 2023/6/4 0:54
 **/
@Import({MybatisPlusConfig.class, Swagger2Config.class})
@EnableConfigurationProperties({MinioConfig.class, RedissonConfig.class})
@Configuration
public class DefaultBeanInitiating {
    @Bean
    public DataSource dataSource(CommonConfigManger commonConfigManger) {
        return commonConfigManger.getCommonConfigProperties().mysql();
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
