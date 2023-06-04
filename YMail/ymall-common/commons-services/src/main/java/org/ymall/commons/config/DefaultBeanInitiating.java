package org.ymall.commons.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * DefaultBeanInitiating
 *
 * @Author IIIDelay
 * @Date 2023/6/4 0:54
 **/
@Import({MybatisPlusConfig.class, Swagger2Config.class})
@Configuration
public class DefaultBeanInitiating {

}
