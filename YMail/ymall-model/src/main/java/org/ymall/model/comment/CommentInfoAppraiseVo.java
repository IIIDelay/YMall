/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.model.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * CommentInfo
 */
@Data
@ApiModel(description = "好评等级")
public class CommentInfoAppraiseVo {

	@ApiModelProperty(value = "好评等级")
	private Integer appraise;

	@ApiModelProperty(value = "个数")
	private Integer count;

}

