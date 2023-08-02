/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ResourceHelper
 *
 * @Author IIIDelay
 * @Date 2023/8/2 22:00
 **/
@Slf4j
public class ResourceHelper {
    /**
     * mediaType
     *
     * @param fileName     fileName
     * @param defaultMedia defaultMedia
     * @return String
     */
    private static String mediaType(String fileName, MediaType defaultMedia) {
        MediaType mediaType = MediaTypeFactory.getMediaType(fileName).orElse(defaultMedia);
        return mediaType.getSubtype();
    }

    /**
     * 文件路径 转  MultipartFile
     *
     * @param filePath filePath
     * @return MultipartFile
     */
    public static MultipartFile getMultipartFile(String filePath) {
        File file = new File(filePath);
        String mediaType = mediaType(file.getName(), MediaType.ALL);
        FileItem item = new DiskFileItemFactory().createItem(file.getName(), mediaType, true, file.getName());
        try {
            InputStream input = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            // 流转移
            IOUtils.copy(input, os);
        } catch (IOException ioe) {
            log.error("getMultipartFile IOException", ioe);
        }
        return new CommonsMultipartFile(item);
    }
}
