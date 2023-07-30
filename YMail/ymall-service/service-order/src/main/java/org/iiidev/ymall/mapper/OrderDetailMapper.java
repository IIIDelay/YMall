/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ymall.model.order.OrderDetail;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
