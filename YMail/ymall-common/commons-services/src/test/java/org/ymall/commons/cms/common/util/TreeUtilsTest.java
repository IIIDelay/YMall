/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.beans.BeanHelper;
import org.springframework.beans.BeanUtils;
import org.ymall.commons.cms.common.entity.TreeNode;
import org.ymall.commons.cms.common.lang.ITree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * TreeUtilsTest
 *
 * @Author IIIDelay
 * @Date 2023/8/30 23:25
 **/
public class TreeUtilsTest {

    @Test
    public void createNode() {
        List<Dept> list = new ArrayList<>();
        Dept dept1 = new Dept();
        dept1.setDeptId(1);
        dept1.setParentId(0);
        dept1.setPhone("xiaomi");
        dept1.setName("xiaomi");

        list.add(dept1);
        Dept dept2 = new Dept();
        dept2.setDeptId(2);
        dept2.setParentId(1);
        dept2.setPhone("huawei");
        dept2.setName("huawei");

        list.add(dept2);
        Dept dept3 = new Dept();
        dept3.setDeptId(3);
        dept3.setParentId(1);
        dept3.setPhone("summing");
        dept3.setName("summing");

        list.add(dept3);
        Dept dept4 = new Dept();
        dept4.setDeptId(4);
        dept4.setParentId(2);
        dept4.setPhone("meizu");
        dept4.setName("meizu");

        list.add(dept4);
        Dept dept5 = new Dept();
        dept5.setDeptId(5);
        dept5.setParentId(4);
        dept4.setPhone("pingguo");
        dept4.setName("pingguo");
        list.add(dept5);

        List<Dept> depts = doCreateNode(list,0);

        System.out.println("node = " + JSON.toJSONString(depts));
    }

    private static <IN extends TreeNode<T>,T> List<IN> doCreateNode(List<? extends IN> root, T parentId) {
        List<IN> collect = root.stream()
            .filter(e -> e.getParentId().equals(parentId))
            .collect(Collectors.toList());

        for (IN in : collect) {
            List<IN> ins = doCreateNode(root, in.getId());
            in.setChildList((List<TreeNode<T>>) ins);
        }
        return collect;
    }

    @Data
    public static class Dept extends TreeNode<Integer>{

        private int deptId;
        private int parentId;
        private String phone;
        private String name;

        @Override
        public int compareTo(@NotNull TreeNode<Integer> o) {
            return 0;
        }

        public Integer getParentId() {
            return parentId;
        }

        @Override
        public Integer getId() {
            return deptId;
        }
    }
}