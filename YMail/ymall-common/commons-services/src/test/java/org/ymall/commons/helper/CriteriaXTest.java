/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CriteriaXTest
 *
 * @Author IIIDelay
 * @Date 2023/9/3 10:33
 **/
public class CriteriaXTest {
    @Test
    public void testCriteria() {
        CriteriaX criteriaX = CriteriaX.build();

        criteriaX.nilIs("age", 18);
        Criteria criteria = Criteria.where("name").is("zhangsan");
        Criteria criteria1 = Criteria.where("name").is("lisi");
        Criteria criteria2 = Criteria.where("name").is("wangwu");
        Criteria criteria3 = Criteria.where("name").is("赵六");
        criteriaX.nilOr(criteria, criteria1);
        criteriaX.nilOr(criteria2);
        criteriaX.nilOr(criteria3);
        criteriaX.nilWhere("this.lastUpdateTime > this.lastReadTime");
        String json = criteriaX.get().getCriteriaObject().toJson();
        System.out.println("json = " + json);
    }
}