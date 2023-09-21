/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.NumberUtil;
import com.google.common.collect.Lists;
import com.google.zxing.common.detector.MathUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.util.PatternMatchUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringHelper
 *
 * @Author IIIDelay
 * @Date 2023/9/10 16:40
 **/
public class StringHelper {
    public static long countMatchRegex(String instr, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(instr);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

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
        DecimalFormat format = new DecimalFormat();

        format.setGroupingUsed(true);
        BigDecimal bigDecimal = new BigDecimal("111111111111111111111111111111111111111111111111");
        String format1 = format.format(bigDecimal);
        System.out.println("format = " + format1);

        Formatter formatter = new Formatter();
        formatter.format("sssa%s", "xx");
        System.out.println("formatter.toString() = " + formatter.toString());


        String s2 = StringUtils.stripToEmpty("    a  a   ");
        String s3 = StringUtils.trimToEmpty("  a  a  ");
        System.out.println("s2 = " + s2);
        System.out.println("s3 = " + s3);
        System.out.println("s2.equals(s3) = " + s2.equals(s3));

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
