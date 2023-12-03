package org.iiidev.ymall.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrPool;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.Failable;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * HttpClientHelper
 *
 * @Author IIIDelay
 * @Date 2023/12/3 11:35
 **/
public class HttpClientHelper {
    private static int requestTimeOut = 3000;
    private static int connectTimeOut = 10000;

    public static void doPostDownload(String url) {
        doPostExecute(url, null, null, (respDto, httpEntity) -> respDto.setInputStream(httpEntity.getContent()));
    }

    public static HttpResponseDTO doPost(String url, String jsonBody) {
        return doPost(url, null, null, null, jsonBody);
    }

    public static HttpResponseDTO doPost(String url, Object pathParam, Map<String, Object> queryParamMap,
                                         Map<String, String> headerMap, String jsonBody) {
        String httpUrl = buildHttpUrl(url, pathParam, queryParamMap);
        if (MapUtils.isEmpty(headerMap)) {
            headerMap = Maps.newLinkedHashMap();
        }
        headerMap.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        return doPostExecute(httpUrl, headerMap, jsonBody, (respDto, httpEntity) -> respDto.setBody(EntityUtils.toString(httpEntity, StandardCharsets.UTF_8)));
    }

    public static HttpResponseDTO doGet(String url, Object pathParam, Map<String, Object> queryParamMap,
                                        Map<String, String> headerMap) {
        String httpUrl = buildHttpUrl(url, pathParam, queryParamMap);
        return doGetExecute(httpUrl, headerMap);
    }

    private static HttpResponseDTO doGetExecute(String url, Map<String, String> headerMap) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpGet httpPost = new HttpGet(url);
            if (MapUtils.isNotEmpty(headerMap)) {
                headerMap.forEach((key, val) -> httpPost.setHeader(key, val));
            }
            httpPost.setConfig(buildRequestConfig(requestTimeOut, connectTimeOut));
            httpResponse = httpClient.execute(httpPost);
            StatusLine statusLine = httpResponse.getStatusLine();
            String respBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            return new HttpResponseDTO(statusLine.getStatusCode(), respBody, null);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            IOUtils.closeQuietly(httpResponse);
            IOUtils.closeQuietly(httpClient);
        }
    }

    private static HttpResponseDTO doPostExecute(String url, Map<String, String> headerMap, String jsonBody,
                                                 FailableBiConsumer<HttpResponseDTO, HttpEntity, Exception> httpEntityPeek) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            headerMap.forEach((key, val) -> httpPost.setHeader(key, val));

            if (StringUtils.isNotBlank(jsonBody)) {
                httpPost.setEntity(new StringEntity(jsonBody));
            }
            httpPost.setConfig(buildRequestConfig(requestTimeOut, connectTimeOut));
            httpResponse = httpClient.execute(httpPost);
            StatusLine statusLine = httpResponse.getStatusLine();
            HttpResponseDTO responseDTO = new HttpResponseDTO();
            httpEntityPeek.accept(responseDTO, httpResponse.getEntity());
            responseDTO.setHttpStatus(statusLine.getStatusCode());
            return responseDTO;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            IOUtils.closeQuietly(httpResponse);
            IOUtils.closeQuietly(httpClient);
        }
    }

    private static RequestConfig buildRequestConfig(int requestTimeOut, int connectTimeOut) {
        return RequestConfig.custom()
            .setConnectionRequestTimeout(requestTimeOut)
            .setConnectTimeout(connectTimeOut)
            .build();
    }

    public static String buildHttpUrl(String url, Object pathParam, Map<String, Object> queryParamMap) {
        StringBuffer sbr = new StringBuffer();
        Assert.notEmpty(url, () -> new RuntimeException("input param url not empty."));
        sbr.append(StringUtils.stripEnd(url, String.valueOf(StrPool.C_SLASH)));
        if (ObjectUtils.isNotEmpty(pathParam)) {
            sbr.append(StrPool.C_SLASH).append(pathParam);
        }

        if (MapUtils.isNotEmpty(queryParamMap)) {
            sbr.append("?");
            queryParamMap.forEach((key, val) -> sbr.append(key).append("=").append(val).append("&"));
        }

        return StringUtils.stripEnd(sbr.toString(), "&");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HttpResponseDTO implements AutoCloseable {
        private int httpStatus;
        private String body;
        private InputStream inputStream;

        @Override
        public void close() throws Exception {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}