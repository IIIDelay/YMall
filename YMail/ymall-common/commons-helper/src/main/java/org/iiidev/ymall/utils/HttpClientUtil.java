/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private static final int CONNECT_TIME_OUT;

    static {
        CONNECT_TIME_OUT = 3000;
    }


    /**
     * doGet
     *
     * @param url url
     * @return String
     */
    public static String doGet(String url) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
                httpclient.close();
                return result;
            }
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }


    /**
     * download
     *
     * @param url      url
     * @param fileName fileName
     */
    public static void download(String url, String fileName) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(CONNECT_TIME_OUT)
            .build();
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();

                // String result = EntityUtils.toString(entity, "UTF-8");
                byte[] bytes = EntityUtils.toByteArray(entity);
                File file = new File(fileName);
                //  InputStream in = entity.getContent();
                FileOutputStream fout = new FileOutputStream(file);
                fout.write(bytes);

                EntityUtils.consume(entity);

                httpclient.close();
                fout.flush();
                fout.close();
                return;
            }
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * doGet
     *
     * @param host    host
     * @param path    path
     * @param method  method
     * @param headers headers
     * @param querys  querys
     * @return HttpResponse
     */
    public static HttpResponse doGet(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys) {
        try {
            HttpClient httpClient = wrapClient(host);

            HttpGet request = new HttpGet(buildUrl(host, path, querys));
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }

            return httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * doPost
     *
     * @param host    host
     * @param path    path
     * @param method  method
     * @param headers headers
     * @param querys  querys
     * @param bodys   bodys
     * @return HttpResponse
     */
    public static HttpResponse doPost(String host, String path, String method,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      Map<String, String> bodys) {
        try {
            HttpClient httpClient = wrapClient(host);
            HttpPost request = new HttpPost(buildUrl(host, path, querys));
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
            if (bodys != null) {
                List<NameValuePair> nameValuePairList = new ArrayList<>();

                for (String key : bodys.keySet()) {
                    nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
                }
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
                formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
                request.setEntity(formEntity);
            }

            return httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * doPost
     *
     * @param host    host
     * @param path    path
     * @param method  method
     * @param headers headers
     * @param querys  querys
     * @param body    body
     * @return HttpResponse
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path, String method,
                                      Map<String, String> headers, Map<String, String> querys,
                                      String body) {
        try {
            HttpClient httpClient = wrapClient(host);

            HttpPost request = new HttpPost(buildUrl(host, path, querys));
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }

            if (StringUtils.isNotBlank(body)) {
                request.setEntity(new StringEntity(body, "utf-8"));
            }

            return httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse doPost(String host, String path, String method,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      byte[] body) {
        try {
            HttpClient httpClient = wrapClient(host);

            HttpPost request = new HttpPost(buildUrl(host, path, querys));
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }

            if (body != null) {
                request.setEntity(new ByteArrayEntity(body));
            }

            return httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * doPut
     *
     * @param host    host
     * @param path    path
     * @param method  method
     * @param headers headers
     * @param querys  querys
     * @param body    body
     * @return HttpResponse
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     String body) {
        try {
            HttpClient httpClient = wrapClient(host);

            HttpPut request = new HttpPut(buildUrl(host, path, querys));
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }

            if (StringUtils.isNotBlank(body)) {
                request.setEntity(new StringEntity(body, "utf-8"));
            }

            return httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse doPut(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     byte[] body) {
        try {
            HttpClient httpClient = wrapClient(host);

            HttpPut request = new HttpPut(buildUrl(host, path, querys));
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }

            if (body != null) {
                request.setEntity(new ByteArrayEntity(body));
            }

            return httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * doDelete
     *
     * @param host    host
     * @param path    path
     * @param method  method
     * @param headers headers
     * @param querys  querys
     * @return HttpResponse
     */
    public static HttpResponse doDelete(String host, String path, String method,
                                        Map<String, String> headers,
                                        Map<String, String> querys) {
        try {
            HttpClient httpClient = wrapClient(host);

            HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }

            return httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
        if (host.startsWith("https://")) {
            sslClient(httpClient);
        }

        return httpClient;
    }

    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 去除url后的?及参数字符串，返回纯url
     *
     * @param url
     * @return
     */
    public static String getNoParamsUrl(String url) {
        String[] split = url.split("\\?", 2);
        return split[0];
    }

}
