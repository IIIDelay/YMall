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


import java.util.List;

/**
 * <p>
 * BaseAttrInfo
 * </p>
 */
@Data
@ApiModel(description = "平台属性")
@TableName("base_attr_info")
public class BaseAttrInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性名称")
    @TableField("attr_name")
    private String attrName;

    @ApiModelProperty(value = "分类id")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty(value = "分类层级")
    @TableField("category_level")
    private Integer categoryLevel;

    //	平台属性值集合
    @TableField(exist = false)
    private List<BaseAttrValue> attrValueList;

}

