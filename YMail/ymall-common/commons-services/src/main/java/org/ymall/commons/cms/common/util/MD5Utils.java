

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**

 * @since 2022/06/13 17:15
 **/
public class MD5Utils {
    private MD5Utils() {
    }

    /**
     * 使用MD5算法处理字符串
     *
     * @param text 字符串
     * @return 处理后的字符串
     */
    public static String md5(String text) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(text.getBytes());
            // 16是表示转换为16进制数
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return text;
    }
}
