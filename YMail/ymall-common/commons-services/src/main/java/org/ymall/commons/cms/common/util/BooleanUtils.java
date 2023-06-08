

package org.ymall.commons.cms.common.util;

import org.ymall.commons.cms.common.function.PlainCaller;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 用于批处理结果处理
 *

 **/
public class BooleanUtils {
    private BooleanUtils() {
    }

    /**
     * <p>存在一个<code>false</code>即为<code>false</code></p>
     * <p>只有全部为<code>true</code>才为<code>true</code></p>
     *
     * @param coll {@link Boolean}集合实例
     */
    public static boolean isTrue(Collection<Boolean> coll) {
        if (ColUtils.isNotEmpty(coll)) {
            boolean bool = true;
            for (Boolean b : coll) {
                if (b == null) {
                    return false;
                }
                bool = Boolean.logicalAnd(bool, b);
            }
            return bool;
        }
        return false;
    }

    public static boolean notNull(Object... objs) {
        boolean r = true;
        for (Object obj : objs) {
            r &= Objects.nonNull(obj);
        }
        return r;
    }

    public static void ifTrue(boolean bool, PlainCaller caller) {
        Objects.requireNonNull(caller);
        if (bool) {
            caller.handle();
        }
    }

    public static <T> void ifTrue(T t, Predicate<T> predicate, Consumer<T> consumer) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(consumer);
        if (predicate.test(t)) {
            consumer.accept(t);
        }
    }
}
