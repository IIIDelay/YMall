/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.common.configuration;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import func.CheckFunction;
import org.ymall.commons.handler.MybatisInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * MybatisPlus配置类
 *
 */
@EnableTransactionManagement
public class MybatisPlusConfig {
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("org.ymall.pojo");
        factoryBean.setConfiguration(buildConfiguration());
        // 手动添加的插件
        factoryBean.setPlugins(new MybatisInterceptor());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = buildMapperLocations(resolver::getResources, "mapper/**/*Mapper.xml");
        factoryBean.setMapperLocations(resources);
        return factoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        configurer.setBasePackage("org.ymall.**.mapper");
        return configurer;
    }

    private Resource[] buildMapperLocations(CheckFunction<String, Resource[], IOException> function,
                                            String... locationPatterns){
        return Arrays.stream(locationPatterns)
                .filter(Objects::nonNull)
                .flatMap(location -> Arrays.stream(function.exceptionMapping(location)))
                .toArray(Resource[]::new);
    }

    private MybatisConfiguration buildConfiguration() {
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(StdOutImpl.class);
        return configuration;
    }


    /**
     * 分页插件: 3.5版本之后PaginationInterceptor 变更为 PaginationInnerInterceptor
     */
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        return paginationInterceptor;
    }
}
