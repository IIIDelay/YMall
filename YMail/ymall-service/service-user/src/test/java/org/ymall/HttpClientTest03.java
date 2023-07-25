/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HttpClientTest03
 *
 * @author IIIDelay
 * @createTime 2023年04月22日 22:36:00
 */
public class HttpClientTest03 {
    public static void main(String[] args) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("https://www.baidu.com");

            // 创建一个代理
            HttpHost proxy = new HttpHost("190.92.134.146", 8080);
            // 请求配置属性
            RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
            httpGet.setConfig(config);

            HttpEntity entity = httpClient.execute(httpGet).getEntity();
            String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            System.out.println("result = " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
