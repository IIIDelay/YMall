



/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ResourceUtils {
    private static final PathMatchingResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();

    private ResourceUtils() {
    }

    /**
     * 解析mapperLocations
     *
     * @param locations
     * @return
     */
    public static Resource[] resolveMapperLocations(String locations) {
        Optional<String> optional = Optional.ofNullable(locations);
        if (optional.isPresent()) {
            return resolveMapperLocations(StringUtil.split(locations, ","));
        }
        return new Resource[0];
    }

    /**
     * 解析mapperLocations
     *
     * @param locations
     * @return
     */
    public static Resource[] resolveMapperLocations(String[] locations) {
        List<Resource> resources = new ArrayList<>();
        if (locations != null) {
            Arrays.stream(locations).forEach(mapperLocation -> {
                try {
                    resources.addAll(Arrays.asList(RESOLVER.getResources(mapperLocation)));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }
        return resources.toArray(new Resource[0]);
    }

    /**
     * configLocation
     *
     * @param configLocation
     * @return
     */
    public static Resource resolveConfigLocation(String configLocation) {
        return RESOLVER.getResource(configLocation);
    }


    /**
     * 以相对文件路径的方式读取{@code resource}资源目录下的文件资源
     *
     * @param fileLocation 文件相对路径 比方说{@code resource}下的"file.txt"文件 那么参数便为"file.txt"
     * @return 文件的字符内容
     */
    public static String readFileContent(String fileLocation) {
        try (InputStream is = resolveConfigLocation(fileLocation).getInputStream()) {
            return new String(FileCopyUtils.copyToByteArray(is), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(String.format("相对路径{%s}文件读取失败", fileLocation));
        }
    }
}
