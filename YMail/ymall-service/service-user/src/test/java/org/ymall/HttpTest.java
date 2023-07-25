/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall;

import io.vavr.control.Try;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * HttpTest
 *
 * @author IIIDelay
 * @createTime 2023年04月18日 22:02:00
 */
public class HttpTest {
    public static void main(String[] args) {
        String url = "http://localhost:9901/admin/product/getCategory1";
        String s = doGet(url);
        System.out.println("s = " + s);
    }

    private static String doGet(String url) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = Try.of(() -> httpClient.execute(get)).get();
            HttpEntity entity = response.getEntity();
            result = Try.of(() -> EntityUtils.toString(entity)).get();
        } finally {
            Try.run(() -> httpClient.close());
        }
        return result;
    }

    private String doPost() {
        return null;
    }
}
