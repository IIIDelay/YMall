/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import cn.hutool.core.lang.Assert;
import com.google.common.reflect.TypeResolver;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.iiidev.ymall.execption.ServiceRuntimeException;
import org.springframework.core.ResolvableType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

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

    private Class<IN> inClazz;

    public CommMongoRepository() {
        getSuperClazz();
    }

    public IN findOne(Long _id) {
        return mongoTemplate.findOne(Query.query(Criteria.where(ID).is(_id)), inClazz);
    }

    public List<IN> find(Query query) {
        return mongoTemplate.find(query, inClazz);
    }

    private void getSuperClazz() {
        ResolvableType type = ResolvableType.forClass(getClass());
        while (true) {
            if (type.getRawClass().equals(CommMongoRepository.class)) {
                Assert.notEmpty(type.getGenerics(), () -> ServiceRuntimeException.of("实现类未指定泛型"));
                inClazz = (Class<IN>) type.getGeneric(0).resolve();
                break;
            } else {
                type = type.getSuperType();
            }
        }
    }
}