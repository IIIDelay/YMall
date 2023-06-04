/*
 * Copyright (c) 2023, author: IIIDev
 */

package utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class AttrTransferUtil {

    /**
     * getter
     *
     * @param in            in
     * @param getterMapping getterMapping
     * @param defaultVal    defaultVal
     * @return OUT
     */
    public static <IN, OUT> OUT safeGetter(IN in, Function<IN, OUT> getterMapping, OUT defaultVal) {
        return Optional.ofNullable(in).map(getterMapping::apply).orElse(defaultVal);
    }

    /**
     * getter
     *
     * @param in            in
     * @param getterMapping getterMapping
     * @return OUT
     */
    public static <IN, OUT> OUT safeGetter(IN in, Function<IN, OUT> getterMapping) {
        return safeGetter(in, getterMapping, null);
    }

    /**
     * safeSetter
     *
     * @param in     in
     * @param setter setter
     */
    public static <IN> void safeSetter(IN in, Consumer<IN> setter) {
        if (in != null) {
            setter.accept(in);
        }
    }

    /**
     * safeSetterElse
     *
     * @param in           in
     * @param setter       setter
     * @param defaultValue defaultValue
     */
    public static <IN> void safeSetterElse(IN in, Consumer<IN> setter, IN defaultValue) {
        if (in != null) {
            setter.accept(defaultValue);
        }
    }

    /**
     * levelGetter
     *
     * @param in       in
     * @param midFunc  midFunc
     * @param leftFunc leftFunc
     * @return OUT
     */
    public static <IN, IN1, OUT> OUT levelGetter(IN in, Function<IN, IN1> midFunc, Function<IN1, OUT> leftFunc) {
        return safeGetter(safeGetter(in, midFunc), leftFunc);
    }
}
