/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package func;

import java.util.function.Function;

/**
 * CheckFunction
 *
 * @author IIIDelay
 * @createTime 2023年03月01日 18:26:00
 */
@FunctionalInterface
public interface CheckFunction<IN, OUT, EX extends Throwable> {
    OUT apply(IN in) throws EX;

    /**
     * 编译期间异常处理
     *
     * @param in in
     * @return OUT
     */
    default OUT exceptionMapping(IN in) {
        try {
            return apply(in);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 运行期间：执行
     *
     * @param inFunc         inFunc
     * @param exceptionClazz exceptionClazz
     * @return java.util.function.Function<IN, OUT>
     */
    static <IN, OUT, EX> Function<IN, OUT> exceptionWrapper(Function<IN, OUT> inFunc, Class<EX> exceptionClazz) {
        return in -> {
            try {
                return inFunc.apply(in);
            } catch (Throwable e) {
                try {
                    exceptionClazz.cast(e);
                } catch (ClassCastException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            }
        };
    }
}
