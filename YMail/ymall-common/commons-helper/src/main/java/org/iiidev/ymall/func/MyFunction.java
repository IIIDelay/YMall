/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.func;

import java.io.Serializable;
import java.util.function.Function;

/**
 
 * @description
 * @date 2023-06-30 10:12
 */
@FunctionalInterface
public interface MyFunction<T, R> extends Function<T, R>, Serializable {
}
