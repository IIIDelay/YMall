/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MinioConfig
 *
 * @author IIIDelay
 * @createTime 2023年03月08日 17:58:00
 */
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfig {
    private String endpointUrl;

    private String accessKey;

    private String secreKey;

    private String bucketName;
}
