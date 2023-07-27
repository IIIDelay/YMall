/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.ymall.easy.validator.errors.ExcelValidErrors;

public class ExcelValidErrorsResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == ExcelValidErrors.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ModelMap model = mavContainer.getModel();
        return model.getAttribute(BindingResult.MODEL_KEY_PREFIX + "excel");
    }
}
