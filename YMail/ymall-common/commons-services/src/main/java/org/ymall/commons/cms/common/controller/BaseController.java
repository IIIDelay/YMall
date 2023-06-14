



package org.ymall.commons.cms.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.ymall.commons.cms.common.converter.DateConverter;
import org.ymall.commons.cms.common.converter.ListConverter;
import org.ymall.commons.cms.common.entity.AjaxResult;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**

 * @since 2019/12/31 13:13
 **/
public abstract class BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 控制器接收{@link Date}类型参数
     */
    @InitBinder
    public void dateType(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(new DateConverter().convert(text));
            }
        });
    }

    /**
     * 控制器接收{@link List}类型参数
     */
    @InitBinder
    public void listType(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(new ListConverter().convert(text));
            }
        });
    }

    /**
     * 返回成功
     *
     * @return AjaxResult
     */
    protected AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * 返回成功
     *
     * @param data 具体响应体数据
     * @return AjaxResult
     */
    protected AjaxResult success(Object data) {
        return AjaxResult.success(data);
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result) {
        return result ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
