package org.ymall.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.ymall.model.product.BaseAttrInfo;
import org.ymall.model.product.BaseTrademark;
import org.ymall.model.product.SpuPoster;
import org.ymall.service.IManageService;
import result.ServiceResponse;

import java.util.List;
import java.util.Map;

/**
 * ProductApiController
 *
 * @Author IIIDelay
 * @Date 2023/6/4 17:41
 **/
@RequestMapping("admin/product")
@RestController
public class ProductApiController {
    @Autowired
    private IManageService manageService;

    /**
     * 根据spuId 查询map 集合属性
     * @param spuId
     * @return
     */
    @GetMapping("inner/getSkuValueIdsMap/{spuId}")
    public Map getSkuValueIdsMap(@PathVariable("spuId") Long spuId){
        return manageService.getSkuValueIdsMap(spuId);
    }

    //  根据spuId 获取海报数据
    @GetMapping("inner/findSpuPosterBySpuId/{spuId}")
    public List<SpuPoster> findSpuPosterBySpuId(@PathVariable Long spuId){
        return manageService.findSpuPosterBySpuId(spuId);
    }

    /**
     * 通过skuId 集合来查询数据
     * @param skuId
     * @return
     */
    @GetMapping("inner/getAttrList/{skuId}")
    public List<BaseAttrInfo> getAttrList(@PathVariable("skuId") Long skuId){
        return manageService.getAttrList(skuId);
    }

    /**
     * 获取全部分类信息
     * @return
     */
    @GetMapping("getBaseCategoryList")
    public ServiceResponse<List<JSONObject>> getBaseCategoryList(){
        List<JSONObject> list = manageService.getBaseCategoryList();
        return ServiceResponse.ok(list);
    }

    @GetMapping("inner/getTrademark/{tmId}")
    public BaseTrademark getTrademark(@PathVariable("tmId")Long tmId){
        return manageService.getTrademarkByTmId(tmId);
    }

}
