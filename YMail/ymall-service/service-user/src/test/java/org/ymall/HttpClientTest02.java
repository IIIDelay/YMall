/*
 * Copyright (c) 2023, author: IIIDev
 */

package org.ymall;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * HttpClientTest02
 *
 * @author IIIDelay
 * @createTime 2023年04月22日 21:44:00
 */
public class HttpClientTest02 {
    public static void main(String[] args) {
        String url = "https://scpic1.chinaz.net/files/default/imgs/2023-04-12/3fa18592cc00668c_s.jpg";

        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
            HttpEntity entity = httpResponse.getEntity();
            String contentType = entity.getContentType().getValue();
            String suffix = "." + contentType.substring(contentType.indexOf("/") + 1);

            byte[] byteArray = EntityUtils.toByteArray(entity);
            IOUtils.write(byteArray, new FileOutputStream("e://abc" + suffix));
            System.out.println("contentType = " + contentType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
