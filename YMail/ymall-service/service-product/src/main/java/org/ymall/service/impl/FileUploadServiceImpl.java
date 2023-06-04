/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.ymall.common.configuration.manger.ConfigManger;
import org.ymall.commons.config.MinioConfig;
import org.ymall.commons.config.manger.CommonConfigManger;
import org.ymall.service.IFileUploadService;

import java.util.UUID;

/**
 * FileUploadServiceImpl
 *
 * @author IIIDelay
 * @createTime 2023年03月08日 18:04:00
 */
@Service
@Slf4j
public class FileUploadServiceImpl implements IFileUploadService {
    @Autowired
    private CommonConfigManger cm;

    @SneakyThrows
    @Override
    public String upload(MultipartFile file) {
        MinioConfig minioConfig = cm.getMinioConfig();

        //  准备获取到上传的文件路径！
        String url = "";

        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        // MinioClient minioClient = new MinioClient("https://play.min.io", "Q3AM3UQ867SPQQA43P2F",
        // "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG");
        MinioClient minioClient =
            MinioClient.builder()
                .endpoint(minioConfig.getEndpointUrl())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecreKey())
                .build();
        // 检查存储桶是否已经存在
        Boolean isExist =
            minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build());
        if (isExist) {
            log.info("Bucket already exists.");
            System.out.println("Bucket already exists.");
        } else {
            // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioConfig.getBucketName()).build());
        }
        //  定义一个文件的名称 : 文件上传的时候，名称不能重复！
        String fileName = System.currentTimeMillis() + UUID.randomUUID().toString();
        // 使用putObject上传一个文件到存储桶中。
        //  minioClient.putObject("asiatrip","asiaphotos.zip", "/home/user/Photos/asiaphotos.zip");
        minioClient.putObject(
            PutObjectArgs.builder().bucket(minioConfig.getBucketName()).object(fileName).stream(
                    file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());
        //  System.out.println("/home/user/Photos/asiaphotos.zip is successfully uploaded as asiaphotos.zip to
        //  `asiatrip` bucket.");
        //  文件上传之后的路径： http://39.99.159.121:9000/ymall/xxxxxx
        url = minioConfig.getEndpointUrl() + "/" + minioConfig.getBucketName() + "/" + fileName;
        //  将文件上传之后的路径返回给页面！
        return url;
    }
}
