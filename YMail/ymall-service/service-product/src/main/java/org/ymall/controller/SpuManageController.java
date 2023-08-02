/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.ymall.model.product.BaseSaleAttr;
import org.ymall.model.product.SpuInfo;
import org.ymall.model.product.SpuSaleAttr;
import org.ymall.service.IManageService;
import org.iiidev.ymall.result.ServiceResponse;

import java.util.List;

@RestController // @ResponseBody + @Controller
@RequestMapping("admin/product")
public class SpuManageController {

    @Autowired
    private IManageService manageService;

    @GetMapping("{page}/{size}")
    public ServiceResponse<IPage<SpuInfo>> getSpuInfoPage(@PathVariable Long page,
                                                          @PathVariable Long size,
                                                          SpuInfo spuInfo) {
        // 创建一个Page 对象
        Page<SpuInfo> spuInfoPage = new Page<>(page,size);
        // 获取数据
        IPage<SpuInfo> spuInfoPageList = manageService.getSpuInfoPage(spuInfoPage, spuInfo);
        // 将获取到的数据返回即可！
        return ServiceResponse.ok(spuInfoPageList);
    }

    // 销售属性http://api.ymall.com/admin/product/baseSaleAttrList
    @GetMapping("baseSaleAttrList")
    public ServiceResponse baseSaleAttrList(){
        // 查询所有的销售属性集合
        List<BaseSaleAttr> baseSaleAttrList = manageService.getBaseSaleAttrList();

        return ServiceResponse.ok(baseSaleAttrList);
    }

    /**
     * 保存spu
     * @param spuInfo
     * @return
     */
    @PostMapping("saveSpuInfo")
    public ServiceResponse<Void> saveSpuInfo(@RequestBody SpuInfo spuInfo){
        // 调用服务层的保存方法
        manageService.saveSpuInfo(spuInfo);
        return ServiceResponse.ok();
    }

    /**
     * 根据spuId 查询销售属性集合
     * @param spuId
     * @return
     */
    @GetMapping("spuSaleAttrList/{spuId}")
    public ServiceResponse<List<SpuSaleAttr>> getSpuSaleAttrList(@PathVariable("spuId") Long spuId) {
        List<SpuSaleAttr> spuSaleAttrList = manageService.getSpuSaleAttrList(spuId);
        return ServiceResponse.ok(spuSaleAttrList);
    }


}
