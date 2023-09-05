/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

/**
 * CommonMongoRe
 *
 * @Author IIIDelay
 * @Date 2023/9/5 22:31
 **/

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.List;

/**
 * mongodb持久层抽象类
 */
public abstract class CommMongoRepository<IN extends Serializable> {
    private MongoTemplate mongoTemplate;

    private Class<IN> inClazz;

    public abstract Class<IN> init();

    public CommMongoRepository() {
        inClazz = init();
    }

    public IN findOne(Long _id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(_id)), inClazz);
    }

    public List<IN> find(Query query) {
        return mongoTemplate.find(query, inClazz);
    }

}
