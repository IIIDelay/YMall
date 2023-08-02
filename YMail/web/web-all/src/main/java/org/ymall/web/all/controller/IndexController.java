/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.web.all.controller;

import org.iiidev.ymall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.iiidev.ymall.result.Result;
import org.iiidev.ymall.result.ServiceResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;

@Controller
public class IndexController {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping({"/", "index.html"})
    public String index(HttpServletRequest request) {
        // 获取首页分类数据
        ServiceResponse result = productFeignClient.getBaseCategoryList();
        request.setAttribute("list", result.getData());
        return "index/index";
    }

    @GetMapping("createIndex")
    @ResponseBody
    public Result createIndex() {
        //  获取后台存储的数据
        ServiceResponse result = productFeignClient.getBaseCategoryList();
        //  设置模板显示的内容
        Context context = new Context();
        context.setVariable("list", result.getData());

        //  定义文件输入位置
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("D:\\index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  调用process();方法创建模板
        templateEngine.process("index/index", context, fileWriter);
        return Result.ok();
    }

}
