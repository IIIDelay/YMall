/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.model.product;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.ymall.model.base.BaseEntity;


/**
 * <p>
 * BaseCategory3
 * </p>
 */
@Data
@ApiModel(description = "商品三级分类")
@TableName("base_category3")
public class BaseCategory3 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "三级分类名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "二级分类编号")
    @TableField("category2_id")
    private Long category2Id;

}

