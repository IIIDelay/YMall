/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.mapper;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ymall.model.product.SpuSaleAttr;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SpuSaleAttrMapperTest
 *
 * @Author IIIDelay
 * @Date 2023/8/7 22:34
 **/
@SpringBootTest
public class SpuSaleAttrMapperTest {
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Test
    public void selectSpuSaleAttrList() {
        List<Long> ids = Lists.newArrayList();
        LambdaQueryWrapper<SpuSaleAttr> queryWrapper = Wrappers.<SpuSaleAttr>lambdaQuery()
            .ne(SpuSaleAttr::getId, ids)
            .and(wraper -> {
                wraper.in(SpuSaleAttr::getSaleAttrName, "颜色")
                    .or()
                    .eq(SpuSaleAttr::getSpuId, 6L);
            });

        List<SpuSaleAttr> spuSaleAttrs = spuSaleAttrMapper.selectList(queryWrapper);
        System.out.println("JSON.toJSONString(spuSaleAttrs) = " + JSON.toJSONString(spuSaleAttrs));

    }
}