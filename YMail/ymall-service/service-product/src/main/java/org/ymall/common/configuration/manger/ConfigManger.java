/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.common.configuration.manger;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ymall.common.configuration.properties.ConfigProperties;
import org.ymall.commons.config.MinioConfig;

/**
 * ConfigManger
 *
 * @author IIIDelay
 * @createTime 2023年03月01日 21:06:00
 */
@Component
@Getter
public class ConfigManger {
    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private CustomConfig customConfig;
}
