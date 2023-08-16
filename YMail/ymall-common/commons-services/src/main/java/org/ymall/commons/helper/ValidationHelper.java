/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import org.hibernate.validator.HibernateValidator;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * ValidationHelper
 *
 * @Author IIIDelay
 * @Date 2023/8/15 23:48
 **/
public class ValidationHelper {
    public void valid() {
        Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
        validator.unwrap()



    }


}
