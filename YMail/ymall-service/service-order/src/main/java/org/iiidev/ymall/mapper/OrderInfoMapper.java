/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ymall.model.order.OrderInfo;

@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}
