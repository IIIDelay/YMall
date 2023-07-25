/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * IFileUploadService
 *
 * @author IIIDelay
 * @createTime 2023年03月08日 18:03:00
 */
public interface IFileUploadService {

    /**
     * upload
     *
     * @param file file
     * @return
     */
    String upload(MultipartFile file);
}
