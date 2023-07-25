/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.common.configuration.manger;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private CustomConfig customConfig;
}
