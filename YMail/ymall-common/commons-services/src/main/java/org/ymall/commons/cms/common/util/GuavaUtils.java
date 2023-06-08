

package org.ymall.commons.cms.common.util;

import com.google.common.base.CaseFormat;

/**

 **/
public class GuavaUtils {
    private GuavaUtils() {
    }

    /**
     * <p>驼峰式命名法</p>
     * <p>表名与类名 涉及大驼峰转下划线互转</p>
     * <p>字段名与属性名 涉及小驼峰与下划线互转</p>
     *
     * @param firstUpper 期望转换后的驼峰首字母是大写还是小写
     * @param s          下划线连接的英文字符串
     * @return 传入参数{@code true}则转化为首字母大写的大驼峰字符串，否则为首字母小写的小驼峰字符串
     */
    public static String toCamelCase(boolean firstUpper, String s) {
        // 任何形式的下划线命名转化为驼峰式命名
        return CaseFormat.LOWER_UNDERSCORE.to(firstUpper ? CaseFormat.UPPER_CAMEL : CaseFormat.LOWER_CAMEL, s);
    }


    /**
     * 下划线命名法
     *
     * @param s 驼峰命名字符串
     * @return 下划线字符串（全小写）
     */
    public static String toUnderScoreCase(String s) {
        // 驼峰转下划线
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s);
    }
}
