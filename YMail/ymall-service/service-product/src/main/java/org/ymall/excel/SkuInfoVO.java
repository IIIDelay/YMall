/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.ymall.commons.pojo.EasyPoiEntityBase;
import org.ymall.model.product.SkuAttrValue;
import org.ymall.model.product.SkuImage;
import org.ymall.model.product.SkuSaleAttrValue;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ExcelIgnoreUnannotated
public class SkuInfoVO implements Serializable {
    @ExcelProperty(value = "spu主键")
    private Long spuId;

    @ExcelProperty(value = "价格")
    private BigDecimal price;

    @ExcelProperty(value = "名称")
    private String skuName;

    @ExcelProperty(value = "描述")
    private String skuDesc;

    @ExcelProperty(value = "周昂两")
    private String weight;

    private Long tmId;

    @ExcelProperty(value = "三级费雷")
    private Long category3Id;

    @ExcelProperty(value = "图片")
    private String skuDefaultImg;

    @ExcelProperty(value = "是销售")
    private Integer isSale;

    @ExcelProperty(value = "列表")
    List<SkuImage> skuImageList;

    List<SkuAttrValue> skuAttrValueList;

    List<SkuSaleAttrValue> skuSaleAttrValueList;
}

