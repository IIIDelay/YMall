



/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.util;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 有关路径的工具类
 *

 * @since 2019/01/10 19:20
 **/
public class PathStringUtils {
    private final static String PATTERN = String.format("yyyy%sMM%sdd", File.separatorChar, File.separatorChar);

    /**
     * 获取日期格式文件目录
     *
     * @return 日期路径
     */
    public static String createDateDir() {
        return createDateDir(LocalDate.now());
    }

    /**
     * 获取日期格式文件目录
     *
     * @param localDate LocalDate实例
     * @return 日期路径
     */
    public static String createDateDir(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(PATTERN));
    }
}
