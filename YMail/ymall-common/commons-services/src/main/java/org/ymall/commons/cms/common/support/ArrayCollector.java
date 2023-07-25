

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.support;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 数组收集器
 *

 **/
public class ArrayCollector<T> implements Collector<T, List<T>, T[]> {
    private static final Set<Characteristics> CHARACTERISTICS = Collections.emptySet();
    private final Class<T> elementType;


    public ArrayCollector(final Class<T> clazz) {
        this.elementType = clazz;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return CHARACTERISTICS;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public Function<List<T>, T[]> finisher() {
        return e -> e.toArray((T[]) Array.newInstance(elementType, e.size()));
    }

    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }
}
