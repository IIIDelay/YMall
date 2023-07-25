/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
public class SkuInfoVO extends EasyPoiEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "spu主键")
    @ExcelProperty(value = "spu主键", index = -3)
    private Long spuId;

    @Excel(name = "价格")
    @ExcelProperty(value = "spu主键", index = -2)
    private BigDecimal price;

    @Excel(name = "sku名称")
    @ExcelProperty(value = "spu主键", index = -1)
    private String skuName;

    @Excel(name = "sku描述")
    @ExcelProperty(value = "spu主键", index = 0)
    private String skuDesc;

    @Excel(name = "重量")
    @ExcelProperty(value = "spu主键", index = 1)
    private String weight;

    private Long tmId;

    @Excel(name = "三级分类")
    @ExcelProperty(value = "spu主键", index = 2)
    private Long category3Id;

    @Excel(name = "sku默认图片")
    @ExcelProperty(value = "spu主键", index = 3)
    private String skuDefaultImg;

    @Excel(name = "销售中")
    @ExcelProperty(value = "spu主键", index = 4)
    private Integer isSale;

    @Excel(name = "sku图片列表")
    @ExcelProperty(value = "spu主键", index = 5)
    List<SkuImage> skuImageList;

    List<SkuAttrValue> skuAttrValueList;

    List<SkuSaleAttrValue> skuSaleAttrValueList;
}

