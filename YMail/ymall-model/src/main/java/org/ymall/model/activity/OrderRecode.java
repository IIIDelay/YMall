/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.model.activity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRecode implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private SeckillGoods seckillGoods;

	private Integer num;

	private String orderStr;
}
