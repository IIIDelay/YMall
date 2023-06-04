/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * CategoryTrademarkVo
 *
 * @author IIIDelay
 * @createTime 2023年03月08日 14:39:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTrademarkVo {
    Long category3Id;

    List<Long> trademarkIdList;
}
