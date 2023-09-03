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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * CriteriaX
 *
 * @Author IIIDelay
 * @Date 2023/9/1 23:27
 **/
public class CriteriaX {
    private Criteria finalCriteria;
    private List<Criteria> orCriteriaList;
    private List<Criteria> andCriteriaList;

    private CriteriaX() {
        finalCriteria = new Criteria();
    }

    public <IN> CriteriaX nilIs(String key, IN in) {
        if (StringUtils.isBlank(key) && in == null) {
            return this;
        }
        finalCriteria.and(key).is(in);
        nilIn(null, Lists.newArrayList());
        return this;
    }

    public <IN> CriteriaX nilIn(String key, Collection<IN> ins) {
        if (StringUtils.isBlank(key) && CollectionUtils.isEmpty(ins)) {
            return this;
        }
        finalCriteria.and(key).in(ins);
        return this;
    }

    public <IN> CriteriaX nilIn(String key, IN... ins) {
        if (StringUtils.isBlank(key) && ArrayUtils.isEmpty(ins)) {
            return this;
        }
        finalCriteria.and(key).in(ins);
        return this;
    }

    public <IN> CriteriaX nilBetweenEq(String key, IN left, IN right) {
        if (StringUtils.isBlank(key) && ArrayUtil.hasEmpty(left, right)) {
            return this;
        }
        finalCriteria.and(key).gte(left).lte(right);
        return this;
    }

    public <IN> CriteriaX nilBetween(String key, IN left, IN right) {
        return nilBetween(key, left, right, true);
    }

    public <IN> CriteriaX nilBetween(String key, IN left, IN right, boolean isIncludeEq) {
        if (StringUtils.isBlank(key) && ArrayUtil.hasEmpty(left, right)) {
            return this;
        }
        if (isIncludeEq) {
            finalCriteria.and(key).gte(left).lte(right);
        } else {
            finalCriteria.and(key).gt(left).lt(right);
        }
        return this;
    }

    public <IN> CriteriaX nilOr(Criteria... orCriterias) {
        Criteria orCriteria = new Criteria().orOperator(orCriterias);
        if (CollectionUtils.isEmpty(orCriteriaList)) {
            orCriteriaList = new ArrayList<>();
        }
        orCriteriaList.add(orCriteria);
        return this;
    }

    public CriteriaX nilAnd(Criteria... andCriterias) {
        Criteria andCriteria = new Criteria().andOperator(andCriterias);
        if (CollectionUtils.isEmpty(andCriteriaList)) {
            andCriteriaList = new ArrayList<>();
        }
        andCriteriaList.add(andCriteria);
        return this;
    }

    /**
     * nilWhere
     *
     * @param conditionEL "this.lastUpdateTime > this.lastReadTime"
     * @return CriteriaX
     */
    public CriteriaX nilWhere(String conditionEL) {
        if (StringUtils.isBlank(conditionEL)) {
            return this;
        }

        finalCriteria.and("$where").is(conditionEL);
        return this;
    }

    public static CriteriaX build() {
        CriteriaX criteriaX = new CriteriaX();
        return criteriaX;
    }

    public Criteria get() {
        if (CollectionUtils.isNotEmpty(orCriteriaList)) {
            finalCriteria.orOperator(orCriteriaList);
        }
        if (CollectionUtils.isNotEmpty(andCriteriaList)) {
            finalCriteria.orOperator(andCriteriaList);
        }
        return finalCriteria;
    }
}
