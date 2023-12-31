/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.function.Supplier;

/**
 * AttrCheckUtil
 *
 * @Author IIIDelay
 * @Date 2023/5/29 23:10
 **/
public class AttrCheckUtil {
    /**
     * insertDuplicateMPVerify: 只要重复不能修改
     *
     * @param pair pair
     * @return List<IN>
     */
    public static <IN, OUT> List<IN> insertDuplicateMPVerify(Supplier<BaseMapper<IN>> supplier, Pair<SFunction<IN, OUT>, OUT> pair) {

        LambdaQueryWrapper<IN> queryWrapper = Wrappers.<IN>lambdaQuery().eq(pair.getLeft(), pair.getRight());
        return supplier.get().selectList(queryWrapper);
    }

    /**
     * updateDuplicateMPVerify : id 不同, column相同,则不能修改
     *
     * @param supplier   supplier
     * @param idPair     idPair
     * @param columnPair columnPair
     * @return List<IN>
     */
    public static <IN, OUT, OUT1> List<IN> updateDuplicateMPVerify(Supplier<BaseMapper<IN>> supplier, Pair<SFunction<IN, OUT>, OUT> idPair,
                                                                   Pair<SFunction<IN, OUT1>, OUT> columnPair) {
        LambdaQueryWrapper<IN> queryWrapper = Wrappers.<IN>lambdaQuery()
            .ne(idPair.getLeft(), idPair.getRight())
            .eq(columnPair.getLeft(), columnPair.getRight());
        return supplier.get().selectList(queryWrapper);
    }

    /**
     * insertDuplicateMGVerify
     *
     * @param mongoTemplate mongoTemplate
     * @param parmPair      parmPair
     * @param delFlag       delFlag
     * @param outClass      outClass
     * @return List<OUT>
     */
    public static <IN, OUT> List<OUT> insertDuplicateMGVerify(MongoTemplate mongoTemplate, Pair<String, IN> parmPair, Pair<String, Integer> delFlag, Class<OUT> outClass) {
        Query query = Query.query(Criteria.where(parmPair.getLeft()).is(parmPair.getRight())
            .and(delFlag.getLeft()).is(delFlag.getRight()));
        return mongoTemplate.find(query, outClass);
    }
}
