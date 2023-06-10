package utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 单元测试，assert 断言工具
 */
public class AssertUtils {

    /**
     * 比对两个对象的属性是否一致
     * <p>
     * 注意，如果 expected 存在的属性，actual 不存在的时候，会进行忽略
     *
     * @param expected     期望对象
     * @param actual       实际对象
     * @param ignoreFields 忽略的属性数组
     */
    public static void assertPojoEquals(Object expected, Object actual, String... ignoreFields) {
        Field[] expectedFields = Optional.ofNullable(ReflectUtil.getFields(expected.getClass())).orElse();
        Arrays.stream(expectedFields).forEach(expectedField -> {
            // 忽略 jacoco 自动生成的 $jacocoData 属性的情况
            if (expectedField.isSynthetic()) {
                return;
            }
            // 如果是忽略的属性，则不进行比对
            if (ArrayUtil.contains(ignoreFields, expectedField.getName())) {
                return;
            }
            // 忽略不存在的属性
            Field actualField = ReflectUtil.getField(actual.getClass(), expectedField.getName());
            if (actualField == null) {
                return;
            }
            // 比对
            Assertions.assertEquals(
                ReflectUtil.getFieldValue(expected, expectedField),
                ReflectUtil.getFieldValue(actual, actualField),
                String.format("Field(%s) 不匹配", expectedField.getName())
            );
        });
    }

    /**
     * 比对两个对象的属性是否一致
     * <p>
     * 注意，如果 expected 存在的属性，actual 不存在的时候，会进行忽略
     *
     * @param expected     期望对象
     * @param actual       实际对象
     * @param ignoreFields 忽略的属性数组
     * @return 是否一致
     */
    public static boolean isPojoEquals(Object expected, Object actual, String... ignoreFields) {
        Field[] expectedFields = ReflectUtil.getFields(expected.getClass());
        return Arrays.stream(expectedFields).allMatch(expectedField -> {
            // 如果是忽略的属性，则不进行比对
            if (ArrayUtil.contains(ignoreFields, expectedField.getName())) {
                return true;
            }
            // 忽略不存在的属性
            Field actualField = ReflectUtil.getField(actual.getClass(), expectedField.getName());
            if (actualField == null) {
                return true;
            }
            return Objects.equals(ReflectUtil.getFieldValue(expected, expectedField),
                ReflectUtil.getFieldValue(actual, actualField));
        });
    }

}