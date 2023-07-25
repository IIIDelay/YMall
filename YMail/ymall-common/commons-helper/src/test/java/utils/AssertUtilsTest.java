/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package utils;

import cn.hutool.core.collection.ListUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AssertUtilsTest
 *
 * @Author IIIDelay
 * @Date 2023/7/25 20:48
 **/
public class AssertUtilsTest {

    @Test
    public void assertPojoEquals() {
        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3, 4);
        ArrayList<Integer> integers1 = Lists.newArrayList(1, 2, 3, 4);
        boolean equalList = ListUtils.isEqualList(integers, integers1);
        System.out.println("equalList = " + equalList);

    }
}