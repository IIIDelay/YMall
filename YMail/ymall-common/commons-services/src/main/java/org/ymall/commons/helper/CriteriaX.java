/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Collection;

/**
 * CriteriaX
 *
 * @Author IIIDelay
 * @Date 2023/9/1 23:27
 **/
public class CriteriaX extends Criteria {
    Criteria criteria;

    public CriteriaX() {
        criteria = new Criteria();
    }

    public <IN> CriteriaX nilIs(String key, IN in) {
        if (StringUtils.isBlank(key) && in == null) {
            return this;
        }
        criteria.and(key).is(in);
        nilIn(null, Lists.newArrayList());
        return this;
    }

    public <IN> CriteriaX nilIn(String key, Collection<IN> ins) {
        if (StringUtils.isBlank(key) && CollectionUtils.isEmpty(ins)) {
            return this;
        }
        criteria.and(key).in(ins);
        return this;
    }

    public <IN> CriteriaX nilIn(String key, IN... ins) {
        if (StringUtils.isBlank(key) && ArrayUtils.isEmpty(ins)) {
            return this;
        }
        criteria.and(key).in(ins);
        return this;
    }

    public <IN> CriteriaX nilBetweenEq(String key, IN left, IN right) {
        if (StringUtils.isBlank(key) && ArrayUtil.hasEmpty(left, right)) {
            return this;
        }
        criteria.and(key).gte(left).lte(right);
        return this;
    }

    public <IN> CriteriaX nilBetween(String key, IN left, IN right) {
        if (StringUtils.isBlank(key) && ArrayUtil.hasEmpty(left, right)) {
            return this;
        }
        criteria.and(key).gt(left).lt(right);
        return this;
    }

}
