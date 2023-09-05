/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

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

    private Criteria eleMatchCriteria;

    // TODO: 类似spring stopwatch起一个间接性控制是否对象继续构造属性与值
    private Predicate<Criteria> initEleMatchCriteria = criteria -> true;

    private CriteriaX(boolean isBuildEleMatch) {
        finalCriteria = new Criteria();
        if (isBuildEleMatch) {
            eleMatchCriteria = new Criteria();
        }
    }

    public <IN> CriteriaX nilIs(String key, IN in) {
        if (StringUtils.isBlank(key) || in == null) {
            return this;
        }
        construction(key, in, Criteria::is);
        return this;
    }

    public <IN> CriteriaX nilIn(String key, Collection<IN> ins) {
        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(ins)) {
            return this;
        }
        construction(key, ins, Criteria::in);
        return this;
    }

    public <IN> CriteriaX nilIn(String key, IN... ins) {
        if (StringUtils.isBlank(key) || ArrayUtils.isEmpty(ins)) {
            return this;
        }
        construction(key, ins, Criteria::in);
        return this;
    }

    public <IN> CriteriaX nilBetweenEq(String key, IN left, IN right) {
        if (StringUtils.isBlank(key) || ArrayUtil.hasEmpty(left, right)) {
            return this;
        }
        betweenConstruct(key, Pair.of(left,right), Criteria::gte, Criteria::lte);
        return this;
    }

    public <IN> CriteriaX nilBetween(String key, IN left, IN right) {
        if (StringUtils.isBlank(key) || ArrayUtil.hasEmpty(left, right)) {
            return this;
        }
        betweenConstruct(key, Pair.of(left,right), Criteria::gt, Criteria::lt);
        return this;
    }

    public <IN extends Number> CriteriaX nilLt(String key, IN in) {
        if (StringUtils.isBlank(key)) {
            return this;
        }
        construction(key, in, Criteria::lt);
        return this;
    }

    public CriteriaX nilOr(Criteria... orCriterias) {
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
     * @param conditonEl "this.lastUpdateTime > this.lastReadTime"
     * @return CriteriaX
     */
    public CriteriaX nilWhere(String conditonEl) {
        if (StringUtils.isBlank(conditonEl)) {
            return this;
        }
        construction("$where", conditonEl, Criteria::is);
        return this;
    }

    private <IN>void construction(String combinKey, IN val, BiConsumer<Criteria, IN> biPeek) {
        biPeek.accept(finalCriteria.and(combinKey), val);
        if (StringUtils.contains(combinKey, ".")) {
            combinKey =  StringUtils.substringAfterLast(combinKey, ".");
        }
        String finalKey = combinKey;
        Optional.ofNullable(eleMatchCriteria)
            .filter(initEleMatchCriteria)
            .ifPresent(ele -> biPeek.accept(ele.and(finalKey), val));
    }

    private <IN>void betweenConstruct(String combinKey, Pair<IN,IN> pair, BiConsumer<Criteria, IN> leftPeek,
                                      BiConsumer<Criteria, IN> rightPeek) {
        leftPeek.accept(finalCriteria.and(combinKey), pair.getLeft());
        rightPeek.accept(finalCriteria, pair.getRight());

        if (StringUtils.contains(combinKey, ".")) {
            combinKey =  StringUtils.substringAfterLast(combinKey, ".");
        }
        String finalKey = combinKey;
        Optional.ofNullable(eleMatchCriteria)
            .filter(initEleMatchCriteria)
            .ifPresent(ele -> {
                leftPeek.accept(ele.and(finalKey), pair.getLeft());
                rightPeek.accept(ele, pair.getRight());
            });
    }

    public static CriteriaX build() {
        CriteriaX criteriaX = new CriteriaX(false);
        return criteriaX;
    }

    public static CriteriaX build(boolean includeEleMatch) {
        if (includeEleMatch) {
            return new CriteriaX(true);
        }
        return new CriteriaX(false);
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

    public Criteria getEleMatch(){
        if (CollectionUtils.isNotEmpty(orCriteriaList)) {
            eleMatchCriteria.orOperator(orCriteriaList);
        }
        if (CollectionUtils.isNotEmpty(andCriteriaList)) {
            eleMatchCriteria.orOperator(andCriteriaList);
        }
        return eleMatchCriteria;
    }
}
