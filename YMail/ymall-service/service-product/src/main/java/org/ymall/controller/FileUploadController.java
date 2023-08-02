/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.ymall.service.IFileUploadService;
import org.iiidev.ymall.result.ServiceResponse;

@RestController
@RequestMapping("admin/product")
public class FileUploadController {

    @Autowired
    private IFileUploadService fileUploadService;

    //  文件上传控制器
    @PostMapping("fileUpload")
    public ServiceResponse<String> fileUpload(MultipartFile file) throws Exception {
        return ServiceResponse.ok(fileUploadService.upload(file));
    }
}
