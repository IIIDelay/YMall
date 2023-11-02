/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.ibatis.io.Resources;
import org.junit.jupiter.api.Test;
import xin.altitude.cms.common.lang.ITree;
import xin.altitude.cms.common.util.TreeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TreeUtilsTest
 *
 * @Author IIIDelay
 * @Date 2023/10/28 23:15
 **/
public class TreeUtilsTest {
    @Test
    public void test01() {
        NodeData nodeData1 = new NodeData();
        nodeData1.setParentId(0L);
        nodeData1.setName("0001xxx");
        nodeData1.setId(0L);

        NodeData nodeData2 = new NodeData();
        nodeData2.setParentId(0L);
        nodeData2.setName("0002xxx");
        nodeData2.setId(0L);

        NodeData nodeData3 = new NodeData();
        nodeData3.setParentId(0L);
        nodeData3.setName("0002xxx");
        nodeData3.setId(0L);

        List<NodeData> nodeData = Lists.newArrayList(new NodeData());



        // List<TreeNode<Long>> node = TreeUtils.createNode(nodeData, 0L);
    }

    public static void main(String[] args) throws IOException {
        InputStream is = Resources.getResourceAsStream("file/mockData.json");
        String cateJson = IOUtils.toString(is);
        List<PmsCategory> pmsCategories = JSONUtil.toList(cateJson, PmsCategory.class);
        List<PmsCategory> tiled =
            TreeUtil.tiled(pmsCategories, PmsCategory::getChildCategories,
                pmsCategory -> pmsCategory.setChildCategories(null));
        // System.out.println("tiled = " + tiled);

        System.out.println("------------------------");

        List<PmsCategory> nodeDetail = TreeUtils.createNodeDetail(tiled, 0L, PmsCategory::getId, PmsCategory::getParentId, PmsCategory::getChildCategories);
        // System.out.println("nodeDetail = " + JSON.toJSONString(nodeDetail));

        // Map<Long, List<PmsCategory>> collect = tiled.stream()
        //     .collect(Collectors.groupingBy(PmsCategory::getParentId));
        // List<PmsCategory> digui = digui(collect, 0L);

        List<PmsCategory> pmsCategories1 = buildTree(tiled, 0L, PmsCategory::getParentId, PmsCategory::getId, PmsCategory::setChildCategories);

    }

    @Test
    public void name3() {

    }

    @Test
    public void name2() throws IOException {

        InputStream is = Resources.getResourceAsStream("file/mockData.json");
        String cateJson = IOUtils.toString(is);
        List<PmsCategory> pmsCategories = JSONUtil.toList(cateJson, PmsCategory.class);
        List<PmsCategory> tiled =
            TreeUtil.tiled(pmsCategories, PmsCategory::getChildCategories,
                pmsCategory -> pmsCategory.setChildCategories(null));
        // System.out.println("tiled = " + tiled);

        System.out.println("------------------------");

        List<PmsCategory> nodeDetail = TreeUtils.createNodeDetail(tiled, 0L, PmsCategory::getId, PmsCategory::getParentId, PmsCategory::getChildCategories);
        // System.out.println("nodeDetail = " + JSON.toJSONString(nodeDetail));

        // Map<Long, List<PmsCategory>> collect = tiled.stream()
        //     .collect(Collectors.groupingBy(PmsCategory::getParentId));
        // List<PmsCategory> digui = digui(collect, 0L);

        List<PmsCategory> pmsCategories1 = buildTree(tiled, 0L, PmsCategory::getParentId, PmsCategory::getId, PmsCategory::setChildCategories);

    }

    private List<PmsCategory> digui(Map<Long, List<PmsCategory>> map, Long pId){
        List<PmsCategory> pmsCategories = Optional.ofNullable(map.get(pId)).orElse(Lists.newArrayList());

        for (PmsCategory pmsCategory : pmsCategories) {
            pmsCategory.setChildCategories(digui(map, pmsCategory.getId()));
        }

        return pmsCategories;
    }

    public static <IN,I>List<IN> buildTree(Collection<IN> ins, I rootId, java.util.function.Function<IN, I> pidFunc,
                                           Function<IN, I> idFunc,
                                           BiConsumer<IN,List<IN>> childFunc){
        Map<I, List<IN>> group = ins.stream().collect(Collectors.groupingBy(pidFunc));
        List<IN> ins1 = childBuild(group, rootId, idFunc, childFunc);
        System.out.println("JSONUtil.toJsonStr(ins1) = " + JSONUtil.toJsonStr(ins1));
        return ins1;
    }

    private  static <IN,I>List<IN> childBuild(Map<I, List<IN>> group, I rootId,Function<IN, I> idFunc,
                                           BiConsumer<IN,List<IN>> childFunc){
        List<IN> ins = Optional.ofNullable(group.get(rootId)).orElse(Lists.newArrayList());
        for (IN in : ins) {
            childFunc.accept(in, childBuild(group, idFunc.apply(in), idFunc, childFunc));
        }
        return ins;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PmsCategory{
        private static final long serialVersionUID = -55261317392487276L;
        private Long id;
        private Long parentId;


        //-- 分类名称
        private String name;

        //-- 层级
        private Integer catLevel;

        //-- 是否显示[0-不显示，1显示]
        private Integer showStatus;

        //-- 排序
        private Integer sort;

        //-- 图标地址
        private String icon;

        //-- 计量单位
        private String productUnit;

        //-- 商品数量
        private Integer productCount;

        // 子分类: 不在表结构中
        List<PmsCategory> childCategories;

    }

    @Data
    public static class NodeData implements ITree<Long> {
        private Long id;

        private String name;

        private Long parentId;
    }
}
