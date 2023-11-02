/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.generatorTools;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <result>xml
 */
public class IbatisResultMapGenerator {
    private static Pattern humpPattern = Pattern.compile("[A-Z]");// 匹配大写字母的正则
    private static final Map<String, String> TYPE_MAP = new HashMap<>();// java类型和jdbc类型的对应关系

    static {
        // 以下对应规则可根据自己的实际情况做修改
        TYPE_MAP.put("string", "VARCHAR");
        TYPE_MAP.put("boolean", "BIT");
        TYPE_MAP.put("byte", "TINYINT");
        TYPE_MAP.put("short", "SMALLINT");
        TYPE_MAP.put("integer", "INTEGER");
        TYPE_MAP.put("long", "BIGINT");
        TYPE_MAP.put("float", "REAL");
        TYPE_MAP.put("double", "DOUBLE");
        TYPE_MAP.put("date", "DATE");// TIMESTAMP,DATETIME
        TYPE_MAP.put("timestamp", "TIMESTAMP");
        TYPE_MAP.put("time", "TIME");
        TYPE_MAP.put("bigdecimal", "DECIMAL");
    }

    /**
     * 生成ResultMap
     *
     * @param clazz 实体类的Class
     * @return String
     */
    public static String generate(Class<?> clazz) {
        String pkgName = clazz.getName();
        String clazzName = clazz.getSimpleName();
        String resultMapId = Character.toLowerCase(clazzName.charAt(0)) + clazzName.substring(1) + "Map";

        StringBuilder resultMap = new StringBuilder();
        resultMap.append("<resultMap id=\"");
        resultMap.append(resultMapId);
        resultMap.append("\" type=\"");
        resultMap.append(pkgName);
        resultMap.append("\">\n");

        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            String property = f.getName();
            String javaType = f.getType().getSimpleName();
            if ("serialVersionUID".equals(property)) {
                continue;// 忽略掉这个属性
            }
            resultMap.append("    <result column=\"");
            resultMap.append(property2Column(property));
            resultMap.append("\" jdbcType=\"");
            resultMap.append(javaType2jdbcType(javaType.toLowerCase()));
            resultMap.append("\" property=\"");
            resultMap.append(property);
            resultMap.append("\" />\n");
        }
        resultMap.append("</resultMap>");
        return resultMap.toString();
    }

    // 驼峰转下划线命名
    private static String property2Column(String property) {
        Matcher matcher = humpPattern.matcher(property);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    // 通过java类型获取jdbc类型
    private static String javaType2jdbcType(String javaType) {
        String jdbcType = TYPE_MAP.get(javaType);
        return jdbcType == null ? "UNKNOWN" : jdbcType;
    }
}