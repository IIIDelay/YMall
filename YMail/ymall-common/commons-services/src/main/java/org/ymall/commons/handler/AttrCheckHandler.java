/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * AttrCheckHandler
 *
 * @Author IIIDelay
 * @Date 2023/6/7 23:11
 **/
@Component
public class AttrCheckHandler implements ApplicationContextAware {
    private static Validator validator;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.validator = applicationContext.getBean(Validator.class);
    }

    /**
     * entityVerify
     *
     * @param in in
     * @return Set<ConstraintViolation < IN>>
     */
    public static <IN> Set<ConstraintViolation<IN>> entityVerify(IN in) {
        Set<ConstraintViolation<IN>> validate = validator.validate(in);
        validate.forEach(System.out::println);
        return validate;
    }
}
