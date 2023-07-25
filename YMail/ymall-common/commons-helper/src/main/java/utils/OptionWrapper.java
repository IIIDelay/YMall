/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * OptionWrapper
 *
 * @Author IIIDelay
 * @Date 2023/7/24 22:52
 **/
public class OptionWrapper<IN> {
    private Optional<IN> optional;

    public OptionWrapper(Optional<IN> optional) {
        this.optional = optional;
    }

    /**
     * ofNoEmpty
     *
     * @return Optional<IN>
     */
    public static <IN> OptionWrapper<IN> ofNotNone(IN in) {
        Optional<IN> optional;
        if (in instanceof String && StringUtils.isNotEmpty((CharSequence) in)) {
            optional = Optional.of(in);
        } else if (in instanceof Collection && CollectionUtils.isNotEmpty((Collection<IN>) in)) {
            optional = Optional.of(in);
        } else if (in.getClass().isArray() && ArrayUtils.isNotEmpty((IN[]) in)) {
            optional = Optional.of(in);
        } else {
            optional = Optional.ofNullable(in);
        }
        return new OptionWrapper<>(optional);
    }

    /**
     * unwrapToOpt
     *
     * @return Optional<IN>
     */
    public Optional<IN> unwrapToOpt() {
        if (optional == null) {
            return Optional.empty();
        }
        return optional;
    }

    /**
     * ifPresent
     *
     * @param checkConsumer checkConsumer
     */
    public void ifPresent(CheckConsumer<IN, Throwable> checkConsumer) {
        Consumer<IN> consumer = in -> {
            try {
                checkConsumer.accept(optional.get());
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
        };
        optional.ifPresent(consumer);
    }

    public <U> OptionWrapper<U> map(Function<IN, U> function) {
        if (optional == null || optional.get() == null) {
            optional = Optional.empty();
        }
        return ofNotNone(function.apply(optional.get()));
    }

    public void get() {
        // TODO:
    }

    @FunctionalInterface
    public interface CheckConsumer<IN, EX extends Throwable> {
        void accept(IN in) throws EX;
    }
}
