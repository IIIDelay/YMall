package org.ymall.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ymall.entity.SkuInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Test
    public void add() {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(111L);
        BigDecimal bigDecimal = BigDecimal.valueOf(11);
        skuInfo.setPrice(bigDecimal);
        skuInfo.setSkuDesc("dec");
        skuInfo.setCreateTime(LocalDateTime.now());
        skuInfo.setUpdateTime(LocalDateTime.now());
        skuInfoMapper.insert(skuInfo);
    }

    @Test
    public void delete() {
        int i = skuInfoMapper.deleteById(1663911924300513291L);
        System.out.println("i = " + i);

    }

    @Test
    public void update() {
    }
}
