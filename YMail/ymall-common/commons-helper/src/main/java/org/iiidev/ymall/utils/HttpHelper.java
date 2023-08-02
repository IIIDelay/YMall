/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * HttpHelper
 *
 * @Author IIIDelay
 * @Date 2023/7/30 21:17
 **/
@Slf4j
public class HttpHelper {
    // 全局参数
    private static PoolingHttpClientConnectionManager connectionManager = null;
    // 设置请求参数
    private static RequestConfig config;

    private static CloseableHttpClient httpClient;

    static {

        try {

            /* 配置同时支持 HTTP 和 HTPPS */
            SSLContextBuilder sslBuilder = new SSLContextBuilder();
            sslBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslBuilder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
            connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // http请求线程池，最大连接数
            int requestMaxNum = 5000;
            ConnectionConfig connConfig = ConnectionConfig.custom().setCharset(Charset.forName("utf-8")).build();
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(5000).build();
            connectionManager.setDefaultConnectionConfig(connConfig);
            connectionManager.setDefaultSocketConfig(socketConfig);
            // 连接池最大生成连接数
            connectionManager.setMaxTotal(requestMaxNum);
            // 默认设置route最大连接数
            connectionManager.setDefaultMaxPerRoute(requestMaxNum);
            httpClient = getConnection();

            IdleConnectionMonitorThread idleConnectionMonitorThread = new IdleConnectionMonitorThread(connectionManager);
            idleConnectionMonitorThread.setDaemon(true);
            idleConnectionMonitorThread.start();
        } catch (Exception e) {

            log.error("初始化连接池配置异常", e);

        }


    }

    /**
     * 单例模式创建
     */
    private static CloseableHttpClient getConnection() {
        /**
         * 1.连接超时时间
         * 2.从线程池中获取线程超时时间
         * 3.设置数据超时时间
         */
        config = RequestConfig.custom().setConnectTimeout(5000)
            .setConnectionRequestTimeout(500)
            .setSocketTimeout(5000)
            .build();
        // 创建builder
        HttpClientBuilder builder = HttpClients.custom();
        // 管理器是共享的，它的生命周期将由调用者管理，并且不会关闭
        // 否则可能出现Connection pool shut down异常
        builder.setConnectionManager(connectionManager).setConnectionManagerShared(true);
        // 长连接策略
        builder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        // 创建httpClient
        httpClient = builder.setDefaultRequestConfig(config).setRetryHandler((e, i, httpContext) -> {
            // 连接池请求失败时,进行请求重试

            if (i > 3) {
                // 重试超过3次,放弃请求
                log.error("retry has more than 3 time, give up request");
                return false;
            }
            if (e instanceof NoHttpResponseException) {
                // 服务器没有响应,可能是服务器断开了连接,应该重试
                log.error("receive no response from server, retry");
                return true;
            }
            if (e instanceof SSLHandshakeException) {
                // SSL握手异常
                log.error("SSL hand shake exception");
                return false;
            }
            if (e instanceof InterruptedIOException) {
                // 超时
                log.error("InterruptedIOException");
                return false;
            }
            if (e instanceof UnknownHostException) {
                // 服务器不可达
                log.error("server host unknown");
                return false;
            }
            if (e instanceof ConnectTimeoutException) {
                // 连接超时
                log.error("Connection Time out");
                return false;
            }
            if (e instanceof SSLException) {
                log.error("SSLException");
                return false;
            }

            HttpClientContext context = HttpClientContext.adapt(httpContext);
            HttpRequest request = context.getRequest();
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                // 如果请求不是关闭连接的请求
                return true;
            }
            return false;

        }).build();
        return httpClient;
    }


    public static class IdleConnectionMonitorThread extends Thread {
        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 30 sec
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }

    /**
     * @param url
     * @param param
     * @param headers
     * @return
     */
    public static String doGet(String url, Map<String, String> param, Map<String, String> headers) {

        String data = "";
        // 组装参数
        if (param != null && param.size() > 0) {
            for (String key : param.keySet()) {
                data = key + "=" + param.get(key) + "&";
            }
            data = data.substring(0, data.length() - 1);
            // 组装url
            url = url + "?" + data;
        }
        HttpGet getMethod = new HttpGet(url);
        // 设置请求头
        packageHeader(headers, getMethod);

        long start = System.currentTimeMillis();
        CloseableHttpResponse httpResponse = null;

        String result = "";
        try {
            long startTime = System.currentTimeMillis();
            httpResponse = httpClient.execute(getMethod);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                result = EntityUtils.toString(entity);

            } else {
                log.info("请求失败，返回" + httpResponse.getStatusLine().getStatusCode());
            }
            log.info("用时：" + (System.currentTimeMillis() - startTime));
            return result;
        } catch (IOException e) {
            log.error("异常", e);
        } finally {
            // 释放连接
            // httpClient必须releaseConnection,但不是abort。因为releaseconnection是归还连接到到连接池,而abort是直接抛弃这个连接,而且占用连接池的数目。
            getMethod.releaseConnection();
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    httpResponse.close();
                } catch (IOException e) {
                    log.error("doGet>>>>", e);
                }
            }
        }
        log.info("end..Duration MS:" + (System.currentTimeMillis() - start));
        return null;
    }


    /**
     * Description: 封装请求头
     *
     * @param headers
     * @param httpMethod httpPost.setHeader("Cookie", "");
     *                   httpPost.setHeader("Connection", "keep-alive");
     *                   httpPost.setHeader("Accept", "application/json");
     *                   httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
     *                   httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
     *                   httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
     */
    public static void packageHeader(Map<String, String> headers, HttpRequestBase httpMethod) {
        // 封装请求头
        if (headers != null && headers.size() > 0) {
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }


    /**
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static String dotPost(String url, Map<String, String> params, Map<String, String> headers) {
        HttpPost postMethod = new HttpPost(url);
        long start = System.currentTimeMillis();
        CloseableHttpResponse httpResponse = null;
        String resultStr = "";
        try {
            // 设置请求头
            packageHeader(headers, postMethod);
            // 设置请求参数--可以用各种方式传入参数
            packageParam(params, postMethod);
            httpResponse = httpClient.execute(postMethod);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                resultStr = EntityUtils.toString(entity);
            } else {
                log.info("请求失败，返回" + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    httpResponse.close();
                } catch (IOException e) {
                    log.error("dotPost>>>>", e);
                }
            }
        }
        log.info("end..Duration MS:" + (System.currentTimeMillis() - start));
        return resultStr;
    }


    /**
     * packageParam
     *
     * @param params     params
     * @param httpMethod httpMethod
     * @throws UnsupportedEncodingException
     */
    public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
        throws UnsupportedEncodingException {
        // 封装请求参数
        if (params != null && params.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<>();
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        }
    }

    /**
     * doPostJson
     *
     * @param url     url
     * @param json    json
     * @param headers headers
     * @return String
     */
    public static String doPostJson(String url, String json, Map<String, String> headers) {
        String resultStr = "";
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        try {
            // 设置请求头
            packageHeader(headers, httpPost);
            // 创建请求内容
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);

            // 执行http请求
            httpResponse = httpClient.execute(httpPost);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                resultStr = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            log.error("doPostJson.>>>>", e);
        } finally {
            // 释放连接
            httpPost.releaseConnection();
            if (httpResponse != null) {
                try {
                    EntityUtils.consume(httpResponse.getEntity());
                    httpResponse.close();
                } catch (IOException e) {
                    log.error("doPostJson.>>>>", e);
                }
            }
        }
        return resultStr;
    }
}
