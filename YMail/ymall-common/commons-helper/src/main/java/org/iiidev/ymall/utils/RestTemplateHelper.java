/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import cn.hutool.core.lang.Assert;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * RestTemplateHelper
 *
 * @Author IIIDelay
 * @Date 2023/12/21 22:32
 **/
public class RestTemplateHelper {
    private static RestTemplate restTemplate = defaultRest();

    public static void main(String[] args) {
        String s = urlFormat("https://pagead2.googlesyndication.com/getco", ImmutableMap.of("name", "zs"));
        System.out.println("s = " + s);

        ResponseEntity<String> responseEntity = null;
        try {
            Map<String, String> map = new HashMap<>();
            map.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            responseEntity = doGet("https://pagead2.googlesyndication.com/getco", map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("responseEntity = " + responseEntity);
    }

    public static ResponseEntity<String> doGet(String url, Map<String, String> headerMap, Object... queryParams) {
        // uri = "http://localhost:8080/people/selectById?id={id}&XXX={XXX}";
        RequestEntity<?> requestEntity = RequestEntity.get(url)
            .headers(httpHeaders -> headerMap.forEach((k, v) -> httpHeaders.add(k, v)))
            .build();

        ResponseEntity<String> responseEntity = restTemplate
            .exchange(requestEntity, String.class);
        return responseEntity;
    }

    public static ResponseEntity<Resource> doPostDown(String url, String body, Map<String, String> headerMap) {
        Assert.isNull(headerMap, () -> new RuntimeException("input param headerMap not null"));
        RequestEntity<String> requestEntity = RequestEntity.post(url)
            .headers(httpHeaders -> headerMap.forEach((k, v) -> httpHeaders.add(k, v)))
            .body(body);
        return restTemplate.exchange(requestEntity, Resource.class);
    }


    public static ResponseEntity<String> doPost(String url, String body, Map<String, String> headerMap, Object... queryParams) {
        Assert.isNull(headerMap, () -> new RuntimeException("input param headerMap not null"));
        RequestEntity<String> requestEntity = RequestEntity.post(url, queryParams)
            .headers(httpHeaders -> headerMap.forEach((k, v) -> httpHeaders.add(k, v)))
            .body(body);
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        return responseEntity;
    }

    public static String urlFormat(String url, Map<String, Object> queryParamMap) {
        StringBuffer sbr = new StringBuffer();
        queryParamMap.forEach((k, v) -> {
            sbr.append("?").append(k).append("=").append(v);
        });
        return StringUtils.stripEnd(url, "/") + sbr;
    }

    private static RestTemplate defaultRest() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(8))
            .errorHandler(new IgnoreErrHandler())
            .rootUri("")
            .build();
    }

    private static class IgnoreErrHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) {
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse response) {
        }
    }
}