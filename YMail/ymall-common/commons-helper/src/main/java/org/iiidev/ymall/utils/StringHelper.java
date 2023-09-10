/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * StringHelper
 *
 * @Author IIIDelay
 * @Date 2023/9/10 16:40
 **/
public class StringHelper {
    public static String limitJoin(List<String> inStrs, String split, int limitLen){
        if (CollectionUtils.isEmpty(inStrs)) {
            return StringUtils.EMPTY;
        }
        String outStr = StringUtils.join(inStrs, split);

        while (outStr.length() > limitLen) {
            if (StringUtils.containsNone(outStr, split)) {
                break;
            }
            outStr = StringUtils.substringBeforeLast(outStr, split);
        }

        if (outStr.length() > limitLen) {
            outStr = StringUtils.substring(outStr, 0, limitLen);
        }
        return outStr;
    }

    public static String limitAppend(List<String> inStrs, String split, int limitLen){
        StringBuffer sbr = new StringBuffer();
        inStrs.forEach(str -> {
            if (str.length() >= (limitLen - split.length())) {
                sbr.append(StringUtils.substring(str, 0, limitLen));
                return;
            }
            if (sbr.length() >= limitLen) {
                return;
            }
            sbr.append(str).append(split);
        });
        return StringUtils.stripEnd(sbr.toString(), split);
    }

    public void s(){
        StringBuffer sb = new StringBuffer();
        List<String> strings = Lists.newArrayList("张三", "lisi", "王五", "zhaoliu", "4eeee");
        for (String string : strings) {
            if (string.length() <= 10-1) {
                sb.append(string).append(",");
            }
        }
        String s = StringUtils.stripEnd(sb.toString(), ",");
    }

    public static void main(String[] args) {
        /* StringBuffer sb = new StringBuffer();
        List<String> strings = Lists.newArrayList("张三", "lisi", "王五", "zhaoliu", "4eeee");
        for (String string : strings) {
            if (string.length() <= 10-1 && sb.length() <= 10) {
                sb.append(string).append(",");
            }
        }
        String s = StringUtils.stripEnd(sb.toString(), ",");
        System.out.println("s = " + s); */

        String str = "张三,lisi,王五,zhaoliu";
        List<String> strings = Lists.newArrayList("张三", "lisi", "王五", "zhaoliu", "4eeee");
        List<String> strings1 = Lists.newArrayList("张三lisi王五zhaoliu4eeee");
        String s = limitAppend(strings, ",", 10);
        System.out.println("s = " + s);
        String s1 = limitAppend(strings1, ",", 10);
        System.out.println("s1 = " + s1);


        /* String s = limitJoin(strings, ",", 10);
        System.out.println("s = " + s); */

        /* List<String> strings = Lists.newArrayList("a", "bb", "cc", "dddd", "4eeee");
        String[] strArr = {"a", "bb", "cc", "dddd", "4eeee"};

        String join1 = StringUtils.join(strings, ",");
        String join3 = StringUtils.join(strings, ",");
        String join4 = StringUtils.join(strings);
        String join5 = StringUtils.join(strArr);
        System.out.println("join1 = " + join1); // join1 = a,bb,cc,dddd,4eeee
        System.out.println("join3 = " + join3); // join3 = a,bb,cc,dddd,4eeee
        // TIP: 集合会出现此问题
        System.out.println("join4 = " + join4); // join4 = [a, bb, cc, dddd, 4eeee]
        System.out.println("join5 = " + join5); // join5 = abbccdddd4eeee


        String join2 = StrUtil.join("a", "bb", "cc", "dddd", "4eeee");
        System.out.println("join2.equals(join1) = " + join2.equals(join1));

        String join = StrUtil.fixLength(join1, ',', 10);
        System.out.println("join1 = " + join1);
        System.out.println(join); */

    }

}
