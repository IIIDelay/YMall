package org.ymall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuSpuInfo {
    @TableId(type = IdType.AUTO)
    private Long id;

    private BigDecimal price;

    private String skuName;

    private Long spuId;

    private List<SpuAttr> spuInfos;


}
