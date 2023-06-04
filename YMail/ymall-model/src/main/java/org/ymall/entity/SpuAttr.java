package org.ymall.entity;

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