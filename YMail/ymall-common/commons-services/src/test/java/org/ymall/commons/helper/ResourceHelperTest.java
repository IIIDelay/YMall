/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

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
}