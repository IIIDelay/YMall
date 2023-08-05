/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.iiidev.ymall.func.MyFunction;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @description
 * @date 2023-06-30 11:47
 */
public class TestTreeUtils {

    private static final String CHILD_NAME = "children";

    /**
     * @param list             需要转换树的集合
     * @param idNameFunc       主键名字的lambda表达式 如：User::getUserId
     * @param parentIdNameFunc 父id名字的lambda表达式 如：User::getParentId
     * @param parentFlag       父类标识 靠此字段判断谁是父类 一般为0
     * @return {@link List<T>}
     * @date 2023-04-14 17:57:45
     * @description 获取树形结构
     */
    public static <T, R, M> Map<String, Object> buildTree(List<T> list, MyFunction<T, R> idNameFunc, MyFunction<T, R> parentIdNameFunc, M parentFlag) {
        String idName = ConvertUtils.getLambdaFieldName(idNameFunc);
        String parentIdName = ConvertUtils.getLambdaFieldName(parentIdNameFunc);
        // 获取到一级分类
        // 获取到一级分类
        List<T> root = list.stream()
            .filter(item -> String.valueOf(parentFlag).equals(String.valueOf(ReflectionUtils.getFieldValue(item, parentIdName))))
            .collect(Collectors.toList());
        list.removeAll(root);
        Map<String, Object> map = new HashMap<>();
        AtomicInteger index = new AtomicInteger(0);
        root.forEach(item -> {
            Map<String, Object> itemMap = BeanUtil.beanToMap(item);
            Map<String, Object> children = getChildren(itemMap, list, idName, parentIdName);
            itemMap.put(CHILD_NAME, children);
            map.put(String.valueOf(index.get()), itemMap);
            index.getAndIncrement();
        });
        return map;
    }

    /**
     * @param list       需要转换树的集合
     * @param idNameFunc 主键名字的lambda表达式 如：User::getUserId
     * @return {@link List<T>}
     * @date 2023-04-14 17:57:45
     * @description 获取树形结构
     */
    public static <T, R> Map<String, Object> buildTree(List<T> list, MyFunction<T, R> idNameFunc) {
        String idName = ConvertUtils.getLambdaFieldName(idNameFunc);
        String parentIdName = "parentId";
        String parentFlag = "0";
        // 获取到一级分类
        // 获取到一级分类
        List<T> root = list.stream()
            .filter(item -> parentFlag.equals(String.valueOf(ReflectionUtils.getFieldValue(item, parentIdName))))
            .collect(Collectors.toList());
        list.removeAll(root);
        Map<String, Object> map = new HashMap<>();
        AtomicInteger index = new AtomicInteger(0);
        root.forEach(item -> {
            Map<String, Object> itemMap = BeanUtil.beanToMap(item);
            Map<String, Object> children = getChildren(itemMap, list, idName, parentIdName);
            itemMap.put(CHILD_NAME, children);
            map.put(String.valueOf(index.get()), itemMap);
            index.getAndIncrement();
        });
        return map;
    }


    /**
     * @param list         需要转换树的集合
     * @param idName       主键id的名字 如：deptId
     * @param parentIdName 父id的名字 如：parentId
     * @date 2023-04-14 17:55:31
     * @description 递归获取子类
     */
    public static <T> Map<String, Object> getChildren(Map<String, Object> itemMap, List<T> list, String idName, String parentIdName) {
        if (hasChildren(itemMap, list, idName, parentIdName)) {
            List<T> collect = list.stream().filter(item ->
                    String.valueOf(ReflectionUtils.getFieldValue(item, parentIdName))
                        .equals(String.valueOf(itemMap.get(idName))))
                .collect(Collectors.toList());
            Map<String, Object> map = new HashMap<>();
            if (CollUtil.isNotEmpty(collect)) {
                itemMap.put(CHILD_NAME, collect);
                list.removeAll(collect);
                AtomicInteger index = new AtomicInteger(0);
                collect.forEach(item -> {
                    Map<String, Object> childItemMap = BeanUtil.beanToMap(item);
                    Map<String, Object> children = getChildren(childItemMap, list, idName, parentIdName);
                    childItemMap.put(CHILD_NAME, children);
                    map.put(String.valueOf(index.get()), childItemMap);
                    index.getAndIncrement();
                });
            }
            return map;
        }
        return Collections.emptyMap();
    }

    /**
     * @param list         需要转换树形的集合
     * @param idName       id的名字 如：deptId
     * @param parentIdName 父id的名字 如：parentId
     * @return {@link boolean}
     * @date 2023-04-14 17:58:58
     * @description 判断是否拥有子类
     */
    public static <T> boolean hasChildren(Map<String, Object> itemMap, List<T> list, String idName, String parentIdName) {
        return list.stream().anyMatch(item -> {
            String a = String.valueOf(ReflectionUtils.getFieldValue(item, parentIdName));
            String b = String.valueOf(itemMap.get(idName));
            return a.equals(b);
        });
    }
}
