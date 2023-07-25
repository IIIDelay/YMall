/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SpuAttr {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String spuName;

    private String saleAttrName;
}