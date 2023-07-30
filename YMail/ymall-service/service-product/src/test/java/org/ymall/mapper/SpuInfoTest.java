/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.ymall.model.product.SkuInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * SpuInfoTest
 *
 * @Author IIIDelay
 * @Date 2023/5/31 22:03
 **/
@SpringBootTest
public class SpuInfoTest {
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void add() {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(111L);
        BigDecimal bigDecimal = BigDecimal.valueOf(11);
        skuInfo.setPrice(bigDecimal);
        skuInfo.setSkuDesc("dec");
        skuInfo.setCreateTime(new Date());
        skuInfo.setUpdateTime(new Date());
        skuInfoMapper.insert(skuInfo);
    }

    @Test
    public void delete() {
        int i = skuInfoMapper.deleteById(1663911924300513291L);
        System.out.println("i = " + i);

    }

    @Test
    public void update() {
        AggregationOptions options = Aggregation.newAggregationOptions().allowDiskUse(true).build();
        ProjectionOperation project = Aggregation.project();
        GroupOperation group = Aggregation.group("name")
            .last("age").as("age")
            .first("height").as("height");
        Aggregation aggregation = Aggregation.newAggregation(group).withOptions(options);

        mongoTemplate.aggregate(aggregation, "doc", Map.class);
    }
}
