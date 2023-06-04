/*
 * Copyright (c) 2023, author: IIIDev
 */

package func;

import java.util.function.Consumer;

/**
 * CheckConsumer
 *
 * @author IIIDelay
 * @createTime 2023年03月01日 18:49:00
 */
@FunctionalInterface
public interface CheckConsumer<IN, EX extends Throwable> {
    /**
     * accept
     *
     * @param in in
     * @return void
     */
    void accept(IN in) throws EX;

    /**
     * exceptionWrapper
     *
     * @param consumer       consumer
     * @return java.util.function.Consumer<T>
     */
    static <T,EX extends Throwable> Consumer<T> exceptionWrapper(CheckConsumer<T,EX> consumer) {
        return in -> {
            try {
                consumer.accept(in);
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    /**
     * exceptionWrapper
     *
     * @param consumer       consumer
     * @param exceptionClazz exceptionClazz
     * @return java.util.function.Consumer<IN>
     */
    static <IN, EX extends Throwable> Consumer<IN> exceptionWrapper
            (CheckConsumer<IN, EX> consumer, Class<EX> exceptionClazz) {
        return in -> {
            try {
                consumer.accept(in);
            } catch (Throwable e) {
                try {
                    exceptionClazz.cast(e);
                } catch (ClassCastException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

}
