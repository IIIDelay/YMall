/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.config.manger;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ymall.commons.config.MinioConfig;

/**
 * ConfigManger
 *
 * @author IIIDelay
 * @createTime 2023年03月01日 21:06:00
 */
@Component
@Getter
public class CommonConfigManger {
    @Autowired
    private CommonConfigProperties commonConfigProperties;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private CommonCustomConfig commonCustomConfig;
}
