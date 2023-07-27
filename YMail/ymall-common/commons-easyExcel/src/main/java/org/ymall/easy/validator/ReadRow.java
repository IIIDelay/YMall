/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.easy.validator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadRow<T> {

    /**
     * Returns row index of a row in the sheet that contains this cell.Start form 0.
     */
    private final Integer rowIndex;

    private final T data;
}
