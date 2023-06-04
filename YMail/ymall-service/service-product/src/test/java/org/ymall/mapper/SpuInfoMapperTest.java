package org.ymall.mapper;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ymall.entity.SkuSpuInfo;

import java.util.List;

@SpringBootTest
public class SpuInfoMapperTest {
    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Test
    public void page() {
        List<SkuSpuInfo> page = spuInfoMapper.page();
        System.out.println("====================");
        Object json = JSONObject.toJSON(page);
        System.out.println("json = " + json);
    }

}