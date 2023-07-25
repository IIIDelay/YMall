

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.lang;

/**
 * 列表实体类实现{@code ITree}接口抽象方法
 *
 * @param <T> id或者pid的泛型
 **/

public interface ITree<T> {
    /**
     * 获取ID的值
     *
     * @return ID
     */
    T getId();

    /**
     * 获取名称值
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取父ID的值
     *
     * @return 父ID
     */
    T getParentId();

}
