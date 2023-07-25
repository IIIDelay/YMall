/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.util;

import org.ymall.commons.cms.common.lang.AbstractObject;

/**
 * Clone工具类
 *

 * @since 2021/03/24 19:49
 **/
public class CloneUtils {
    private CloneUtils() {
    }

    /**
     * 使用原生clone方法复制对象
     *
     * @param obj 原始对象实例
     * @param <T> 原始对象泛型
     * @return 原始对象的深拷贝
     */
    @SuppressWarnings("unchecked")
    public static <T extends AbstractObject> T clone(T obj) {
        if (obj != null) {
            return (T) obj.clone();
        }
        return null;
    }
}
