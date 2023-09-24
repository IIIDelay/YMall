/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import cn.hutool.core.lang.Assert;
import org.iiidev.ymall.execption.ServiceRuntimeException;
import org.springframework.core.ResolvableType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * mongodb持久层抽象类
 *
 * @Author IIIDelay
 * @Date 2023/9/5 22:31
 **/
public abstract class CommMongoRepository<IN extends Serializable> {
    public static final String ID = "_id";

    @Resource
    private MongoTemplate mongoTemplate;

    private Class<IN> docClazz;

    public CommMongoRepository() {
        getSuperClazz();
    }

    public IN findById(Long _id) {
        return mongoTemplate.findById(_id, docClazz);
    }

    public IN findOne(Query query) {
        return mongoTemplate.findOne(query, docClazz);
    }

    public List<IN> find(Query query) {
        return mongoTemplate.find(query, docClazz);
    }

    public <OUT> List<OUT> aggregate(TypedAggregation<?> aggregation, Class<OUT> outClass) {
        AggregationResults<OUT> aggregate = mongoTemplate.aggregate(aggregation, docClazz, outClass);
        return aggregate.getMappedResults();
    }

    private void getSuperClazz() {
        ResolvableType type = ResolvableType.forClass(getClass());
        while (true) {
            if (type.getRawClass().equals(CommMongoRepository.class)) {
                Assert.notEmpty(type.getGenerics(), () -> ServiceRuntimeException.of("实现类未指定泛型"));
                docClazz = (Class<IN>) type.getGeneric(0).resolve();
                break;
            } else {
                type = type.getSuperType();
            }
        }
    }
}