/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.config.manger;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * InitPrint
 *
 * @author IIIDelay
 * @createTime 2023年03月08日 16:11:00
 */
@Component
@ToString(exclude = "password")
@Slf4j
public class InitPrint implements InitializingBean {
    @Autowired
    private List<RedisConnectionFactory> redisConfigList;

    @Autowired
    private List<DataSource> dataSources;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("======================== init start =========================");
        // log.info(JSON.toJSONString(redisConfigList));
        // log.info(JSON.toJSONString(dataSources));
        log.info("======================== init   end =========================");
    }
}
