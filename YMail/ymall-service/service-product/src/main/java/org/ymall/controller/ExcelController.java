package org.ymall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ymall.commons.helper.EasyPoiUtils;
import org.ymall.excel.SkuInfoVO;
import org.ymall.mapper.SkuInfoMapper;
import org.ymall.model.product.SkuInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        EasyPoiUtils.exportData(response, skuInfoVOS, SkuInfoVO.class,
            "sku excel导出","sku exprot",null);
    }
}
