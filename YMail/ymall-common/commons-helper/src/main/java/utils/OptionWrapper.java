package utils;

import cn.hutool.core.io.LineHandler;
import func.CheckConsumer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * OptionWrapper
 *
 * @Author IIIDelay
 * @Date 2023/7/24 22:52
 **/
public class OptionWrapper<IN> {
    private Optional<IN> optional;

    public static <IN> OptionWrapper<IN> build() {
        OptionWrapper<IN> inOptionWrapper = new OptionWrapper<>();
        return inOptionWrapper;
    }

    /**
     * ofNoEmpty
     *
     * @param in in
     * @return Optional<IN>
     */
    public OptionWrapper<IN> ofNoNon(IN in) {
        if (in instanceof String && StringUtils.isNotEmpty((CharSequence) in)) {
            optional = Optional.of(in);
        } else if (in instanceof Collection && CollectionUtils.isNotEmpty((Collection<IN>) in)) {
            optional = Optional.of(in);
        } else {
            optional = Optional.ofNullable(in);
        }
        return this;
    }

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

    public void get() {
        // TODO:
    }

    @FunctionalInterface
    public interface CheckConsumer<IN, EX extends Throwable> {
        void accept(IN in) throws EX;
    }
}
