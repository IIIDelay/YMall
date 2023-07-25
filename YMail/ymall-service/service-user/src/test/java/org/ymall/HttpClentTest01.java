/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall;

import com.google.common.collect.Lists;
import org.apache.catalina.util.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * HttpClentTest01
 *
 * @author IIIDelay
 * @createTime 2023年04月22日 07:39:00
 */
public class HttpClentTest01 {
    public static void main(String[] args) {
        // 请求参数: username=abc 123|efg 这种情况, httpclient请求会报错, 需要encode
        String requesParm = new URLEncoder().encode("abc 123|efg", StandardCharsets.UTF_8);
        // requesParm = abc%20123%7Cefg
        System.out.println("requesParm = " + requesParm);
        String url = "https://www.huya.com/116" + "?username=" + requesParm;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            // 解决httpclient被认为不是真人行为
            httpGet.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0(windows NT 10.0; Win64; x64)");
            // 防盗链, value: url发生防盗链的网站url
            httpGet.addHeader(HttpHeaders.REFERER, "https://www.huya.com/116");

            HttpEntity entity = httpClient.execute(httpGet).getEntity();
            String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);
            System.out.println("result = " + result);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private static void extracted() {
        String url = "http://localhost:9901/admin/product/test";
        try (CloseableHttpClient htttpClent = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            // post 请求带请求参数
            HttpEntity reqEntity = new UrlEncodedFormEntity(
                Lists.newArrayList(new BasicNameValuePair("username", "zs"), // 请求参数
                    new BasicNameValuePair("name", "xxx"), // form参数
                    new BasicNameValuePair("gender", "1"))); // form参数

            httpPost.setEntity(reqEntity);

            HttpEntity entity = htttpClent.execute(httpPost).getEntity();
            String string = EntityUtils.toString(entity, "utf-8");
            System.out.println("string = " + string);

            // 检测流是否关闭, 实体是否消费
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void http(String url) {
        try (CloseableHttpClient htttpClent = HttpClients.createDefault()) {
            URIBuilder uriBuilder =
                new URIBuilder(url, StandardCharsets.UTF_8).addParameters(Lists.newArrayList(new BasicNameValuePair("q","西游记")));
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            System.out.println("httpGet.getRequestLine() = " + httpGet.getRequestLine());
            httpGet.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
            // 请求配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(3000) // 从连接池获取超时时间
                .setConnectTimeout(3000) // 请求超时时间
                .build();
            httpGet.setConfig(requestConfig);
            HttpEntity entity = htttpClent.execute(httpGet).getEntity();
            String string = EntityUtils.toString(entity, "utf-8");
            System.out.println("string = " + string);
        } catch (Exception exception) {

        }
    }
}
