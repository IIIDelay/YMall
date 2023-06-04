/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.common.configuration.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * ConfigAttr
 *
 * @author IIIDelay
 * @createTime 2023年03月01日 20:48:00
 */
@Component
@RefreshScope // 动态刷新
public class ConfigProperties implements EnvironmentAware{

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public DataSource postgresql() {
        return getDruidDataSource("pg");
    }

    private DruidDataSource getDruidDataSource(String prefix) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(environment.getProperty(prefix+ ".username"));
        dataSource.setPassword(environment.getProperty(prefix+".password"));
        dataSource.setUrl(environment.getProperty(prefix+".url"));
        dataSource.setDriverClassName(environment.getProperty(prefix+".driverClass"));
        return dataSource;
    }

    public DataSource mysql() {
        return getDruidDataSource("mysql");
    }
}
