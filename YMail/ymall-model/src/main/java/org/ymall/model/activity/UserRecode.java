/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.model.activity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRecode implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long skuId;
	
	private String userId;
}
