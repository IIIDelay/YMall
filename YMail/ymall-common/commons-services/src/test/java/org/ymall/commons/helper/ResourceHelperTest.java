/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.ymall.commons.vo.CustomerInfoVO;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ResourceHelperTest
 *
 * @Author IIIDelay
 * @Date 2023/8/2 22:16
 **/
public class ResourceHelperTest {

    @Test
    public void getMultipartFile() {
        String path = "src/test/resources/temp/CategoryInfo .xlsx";
        MultipartFile multipartFile = ResourceHelper.getMultipartFile(path);
        System.out.println("multipartFile = " + multipartFile);
    }

    @Test
    public void readExcl() {
        File file = FileUtils.getFile("src/test/resources/temp/CustomerInfo.xlsx");

        MultipartFile multipartFile = ResourceHelper.getMultipartFile("src/test/resources/temp/CustomerInfo.xlsx");
        List<CustomerInfoVO> customerInfoVOS = EasyExcelUtils.importDefault(multipartFile, CustomerInfoVO.class);
        String jsonString = JSON.toJSONString(customerInfoVOS);
        System.out.println("jsonString = " + jsonString);
    }
}