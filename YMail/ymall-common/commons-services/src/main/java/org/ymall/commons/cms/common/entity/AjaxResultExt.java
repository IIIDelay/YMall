

package org.ymall.commons.cms.common.entity;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import xin.altitude.cms.common.util.FieldFilterUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 操作消息提醒
 *

 */
public class AjaxResultExt extends AjaxResult {

    /**
     * 完成分页对象实体类的属性过滤
     *
     * @param data   原始分页对象实例
     * @param action 方法引用选中需要过滤排除的列
     * @param <T>    原始数据类型
     * @return AjaxResult
     * @since 1.5.7
     */
    @SafeVarargs
    public static <T> AjaxResult success(IPage<T> data, final SFunction<T, ? extends Serializable>... action) {
        return success(data, false, action);
    }

    /**
     * 完成分页对象实体类的属性过滤
     *
     * @param data      原始分页对象实例
     * @param isInclude 如果是true代表保留字段、false代表排除字段
     * @param action    方法引用选中需要过滤排除的列
     * @param <T>       原始数据类型
     * @return AjaxResult
     * @since 1.5.7.2
     */
    @SafeVarargs
    public static <T> AjaxResult success(IPage<T> data, boolean isInclude, final SFunction<T, ? extends Serializable>... action) {
        return AjaxResult.success(SUCCESS_MSG, FieldFilterUtils.filterFields(data, isInclude, action));
    }

    /**
     * 完成对象实体类的属性过滤
     *
     * @param data   原始对象实例
     * @param action 方法引用选中需要过滤排除的列
     * @param <T>    原始数据类型
     * @return AjaxResult
     * @since 1.5.7
     */
    @SafeVarargs
    public static <T> AjaxResult success(T data, final SFunction<T, ? extends Serializable>... action) {
        return success(data, false, action);
    }

    /**
     * 完成对象实体类的属性过滤
     *
     * @param data      原始对象实例
     * @param isInclude 如果是true代表保留字段、false代表排除字段
     * @param action    方法引用选中需要过滤排除的列
     * @param <T>       原始数据类型
     * @return AjaxResult
     * @since 1.5.7.2
     */
    @SafeVarargs
    public static <T> AjaxResult success(T data, boolean isInclude, final SFunction<T, ? extends Serializable>... action) {
        return AjaxResult.success(SUCCESS_MSG, FieldFilterUtils.filterFields(data, isInclude, action));
    }

    /**
     * 完成列表对象实体类的属性过滤
     *
     * @param data   原始列表对象实例
     * @param action 方法引用选中需要过滤排除的列
     * @param <T>    原始数据类型
     * @return AjaxResult
     * @since 1.5.7
     */
    @SafeVarargs
    public static <T> AjaxResult success(List<T> data, final SFunction<T, ? extends Serializable>... action) {
        return success(data, false, action);
    }

    /**
     * 完成列表对象实体类的属性过滤
     *
     * @param data      原始列表对象实例
     * @param isInclude 如果是true代表保留字段、false代表排除字段
     * @param action    方法引用选中需要过滤排除的列
     * @param <T>       原始数据类型
     * @return AjaxResult
     * @since 1.5.7.2
     */
    @SafeVarargs
    public static <T> AjaxResult success(List<T> data, boolean isInclude, final SFunction<T, ? extends Serializable>... action) {
        return AjaxResult.success(SUCCESS_MSG, FieldFilterUtils.filterFields(data, isInclude, action));
    }
}
