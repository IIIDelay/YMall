/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.commons.config.manger;

import com.alibaba.druid.pool.DruidDataSource;
import common.RedisConstants;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.stereotype.Component;
import utils.AttrTransferUtil;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * ConfigAttr
 *
 * @author IIIDelay
 * @createTime 2023年03月01日 20:48:00
 */
@Component
@RefreshScope // 动态刷新
public class CommonConfigProperties implements EnvironmentAware{

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public DataSource postgresql() {
        return getDruidDataSource("pg");
    }

    /**
     * redisConfiguration
     *
     * @param deploymentMode deploymentMode
     * @return RedisConfiguration
     */
    public RedisConfiguration redisConfiguration(RedisConstants.DeploymentMode deploymentMode) {
        if (Objects.equals(deploymentMode, RedisConstants.DeploymentMode.STAND_ALONE)) {
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setHostName(environment.getProperty("redis." + deploymentMode.name + ".hostname"));
            configuration.setPort(AttrTransferUtil.safeGetter(environment.getProperty("redis." + deploymentMode.name + ".port"),
                    Integer::parseInt, 0));
            return configuration;
        }
        return null;
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
