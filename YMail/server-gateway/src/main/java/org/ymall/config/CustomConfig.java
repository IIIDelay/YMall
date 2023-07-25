/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CustomConfig
 *
 * @author IIIDelay
 * @createTime 2023年04月02日 00:12:00
 */
@Component
public class CustomConfig {
    @Value("#{'${gateway.filters.blackList}'.split(',')}")
    private List<String> blackList;

    public List<String> getBlackList() {
        return blackList;
    }
}
