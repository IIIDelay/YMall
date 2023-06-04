package org.ymall.commons.handler;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({
    @Signature(type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class})
})
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("对方法做增强");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("target = " + target);
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        properties.forEach((k, v) -> System.out.println(k + " : " + v));
        Interceptor.super.setProperties(properties);
    }
}
