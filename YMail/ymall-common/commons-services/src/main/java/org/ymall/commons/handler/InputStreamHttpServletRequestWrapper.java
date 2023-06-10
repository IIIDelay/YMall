package org.ymall.commons.handler;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 请求流支持多次获取
 * 在接口调用链中，request的请求流只能调用一次，处理之后，如果之后还需要用到请求流获取数据，就会发现数据为空。
 * 比如使用了filter或者aop在接口处理之前，获取了request中的数据，对参数进行了校验，那么之后就不能在获取request请求流了
 *
 * 解决:
 * 继承HttpServletRequestWrapper，将请求中的流copy一份，复写getInputStream和getReader方法供外部使用。每次调用后的getInputStream方法都是从复制出来的二进制数组中进行获取，这个二进制数组在对象存在期间一致存在。
 * 使用Filter过滤器，在一开始，替换request为自己定义的可以多次读取流的request。
 * 这样就实现了流的重复获取
 */
public class InputStreamHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 用于缓存输入流
     */
    private ByteArrayOutputStream cachedBytes;

    public InputStreamHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null) {
            // 首次获取流时，将流放入 缓存输入流 中
            cacheInputStream();
        }

        // 从 缓存输入流 中获取流并返回
        return new CachedServletInputStream(cachedBytes.toByteArray());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 首次获取流时，将流放入 缓存输入流 中
     */
    private void cacheInputStream() throws IOException {
        // 缓存输入流以便多次读取。为了方便, 我使用 org.apache.commons IOUtils
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }

    /**
     * 读取缓存的请求正文的输入流
     * <p>
     * 用于根据 缓存输入流 创建一个可返回的
     */
    public static class CachedServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream input;

        public CachedServletInputStream(byte[] buf) {
            // 从缓存的请求正文创建一个新的输入流
            input = new ByteArrayInputStream(buf);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        @Override
        public int read() {
            return input.read();
        }
    }

}

