/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.lang;

/**
 * 定义抽象类，单纯实现Cloneable接口
 * 将clone方法修饰符转化为public
 *

 * @since 2021/03/24 19:57
 **/
public abstract class AbstractObject implements Cloneable {
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
