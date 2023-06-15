package org.ymall.common.aspectj;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.ymall.common.annotation.BusLogAnno;
import org.ymall.mapper.BusLogMapper;
import org.ymall.model.product.BusLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// @Component
// @Aspect
@Slf4j
public class BusLogAop implements Ordered {
    @Autowired
    private BusLogMapper busLogMapper;

    /**
     * 定义BusLogAop的切入点为标记@BusLog注解的方法
     */
    @Pointcut(value = "@annotation(org.ymall.common.annotation.BusLogAnno)")
    public void pointcut() {
    }

    /**
     * 业务操作环绕通知
     *
     * @param proceedingJoinPoint
     * @retur
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("----BusAop 环绕通知 start");
        // 执行目标方法
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        // 目标方法执行完成后，获取目标类、目标方法上的业务日志注解上的功能名称和功能描述
        Object target = proceedingJoinPoint.getTarget();
        Object[] args = proceedingJoinPoint.getArgs();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        BusLogAnno anno1 = target.getClass().getAnnotation(BusLogAnno.class);
        BusLogAnno anno2 = signature.getMethod().getAnnotation(BusLogAnno.class);
        BusLog busLogAnnoBean = new BusLog();
        String logName = anno1.name();
        String logDescrip = anno2.descrip();
        busLogAnnoBean.setBusName(logName);
        busLogAnnoBean.setBusDescrip(logDescrip);
        busLogAnnoBean.setOperPerson("fanfu");
        busLogAnnoBean.setOperTime(new Date());
        JsonMapper jsonMapper = new JsonMapper();
        String json = null;
        try {
            json = jsonMapper.writeValueAsString(args);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 把参数报文写入到文件中
        OutputStream outputStream = null;
        try {
            String paramFilePath = System.getProperty("user.dir") + File.separator + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + ".log";
            outputStream = new FileOutputStream(paramFilePath);
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            busLogAnnoBean.setParamFile(paramFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        // 保存业务操作日志信息
        this.busLogMapper.insert(busLogAnnoBean);
        log.info("----BusAop 环绕通知 end");
        return result;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}