/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.ymall.commons.helper.EasyExcelUtils;
import org.ymall.commons.helper.EasyPoiUtils;
import org.ymall.excel.SkuInfoVO;
import org.ymall.mapper.SkuInfoMapper;
import org.ymall.model.product.SkuInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * ExcelController
 *
 * @Author IIIDelay
 * @Date 2023/7/23 12:38
 **/
@RestController
@RequestMapping("excel")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class ExcelController {
    private final SkuInfoMapper skuInfoMapper;

    @GetMapping("sku/export")
    public void exportForSku(HttpServletResponse response) {
        List<SkuInfo> skuInfos = skuInfoMapper.selectList(Wrappers.emptyWrapper());
        List<SkuInfoVO> skuInfoVOS = BeanUtil.copyToList(skuInfos, SkuInfoVO.class);
        long id = Thread.currentThread().getId();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object name = requestAttributes.getRequest().getHeader("name");
        System.out.println(name);
        CompletableFuture.runAsync(() -> {
            long idAysnc = Thread.currentThread().getId();
            RequestContextHolder.setRequestAttributes(requestAttributes);
            ServletRequestAttributes requestAttributes1 = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            Object name1 = requestAttributes1.getRequest().getHeader("name");
            System.out.println("name1 = " + name1);
        });

        // EasyPoiUtils.exportData(response, skuInfoVOS, SkuInfoVO.class,
        //     "sku excel导出","sku exprot",null);

        EasyExcelUtils.write(response, "sku excel导出", null, SkuInfoVO.class, skuInfoVOS);
    }
}
