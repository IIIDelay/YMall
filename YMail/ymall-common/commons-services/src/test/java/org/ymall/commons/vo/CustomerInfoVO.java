/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CustomerInfoVO
 *
 * @Author IIIDelay
 * @Date 2023/8/2 22:56
 **/
@ExcelIgnoreUnannotated
@Data
public class CustomerInfoVO implements Serializable {
    @ExcelProperty(value = "客户名称")
    private String customName;

    @ExcelProperty(value = "客户分类")
    private String customCate;

    @ExcelProperty(value = "联系人")
    private String customConcat;

    @ExcelProperty(value = "地址")
    private String customAddr;

    @ExcelProperty(value = "联系电话")
    private String customPhone;

    @ExcelProperty(value = "其他联系信息")
    private String otherInfo;
}
