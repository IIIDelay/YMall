/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TestTreeUtilsTest
 *
 * @Author IIIDelay
 * @Date 2023/8/6 0:29
 **/
public class TestTreeUtilsTest {

    @Test
    public void buildTree() {
        List<Dept> list = new ArrayList<>();
        Dept dept1 = new Dept();
        dept1.setDeptId(1);
        dept1.setParentId(0);
        list.add(dept1);
        Dept dept2 = new Dept();
        dept2.setDeptId(2);
        dept2.setParentId(1);
        list.add(dept2);
        Dept dept3 = new Dept();
        dept3.setDeptId(3);
        dept3.setParentId(1);
        list.add(dept3);
        Dept dept4 = new Dept();
        dept4.setDeptId(4);
        dept4.setParentId(2);
        list.add(dept4);
        Dept dept5 = new Dept();
        dept5.setDeptId(5);
        dept5.setParentId(4);
        list.add(dept5);

        Map<String, Object> stringObjectMap = TestTreeUtils.buildTree(list, Dept::getDeptId);
        System.out.println(stringObjectMap);
    }

    public static class Dept{

        private int deptId;
        private int parentId;

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getParentId() {
            return parentId;
        }
    }

    public static class DeptVo{

        private int deptId;
        private int parentId;

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getParentId() {
            return parentId;
        }
    }
}