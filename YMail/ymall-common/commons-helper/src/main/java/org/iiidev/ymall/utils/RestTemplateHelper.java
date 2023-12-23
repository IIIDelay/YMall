/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * RestTemplateHelper
 *
 * @Author IIIDelay
 * @Date 2023/12/21 22:32
 **/
public class RestTemplateHelper {
    private static RestTemplate restTemplate = buildDefaultRT();

    public static void main(String[] args) {
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
        RequestEntity<?> requestEntity = RequestEntity.get(url, queryParams).headers(httpHeaders -> headerMap.forEach((k, v) -> httpHeaders.add(k, v))).build();
        ResponseEntity<String> responseEntity = restTemplate
            .exchange(requestEntity, String.class);
        return responseEntity;
    }

    public static ResponseEntity<String> doPost(String url, String body, Map<String, String> headerMap, Object... queryParams) {
        RequestEntity<String> requestEntity = RequestEntity.post(url, queryParams)
            .headers(httpHeaders -> headerMap.forEach((k, v) -> httpHeaders.add(k, v)))
            .body(body);
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        return responseEntity;
    }
    
    private static RestTemplate buildDefaultRT(){
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(8000);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new IgnoreErrHandler());
        return restTemplate;
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