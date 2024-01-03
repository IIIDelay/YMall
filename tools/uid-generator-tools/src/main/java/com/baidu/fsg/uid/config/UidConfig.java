package com.baidu.fsg.uid.config;

import com.baidu.fsg.uid.config.properties.UidProperties;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.baidu.fsg.uid.impl.DefaultUidGenerator;
import com.baidu.fsg.uid.worker.DisposableWorkerIdAssigner;
import com.baidu.fsg.uid.worker.WorkerIdAssigner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * UidConfig
 *
 * @Author IIIDelay
 * @Date 2024/1/3 21:37
 **/
@Configuration
@MapperScan("com.baidu.**.dao")
@EnableConfigurationProperties(UidProperties.class)
public class UidConfig {
    @Bean
    public DefaultUidGenerator defaultUidGenerator(UidProperties uidProperties, WorkerIdAssigner workerIdAssigner) {
        DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator();
        defaultUidGenerator.setWorkerIdAssigner(workerIdAssigner);
        defaultUidGenerator.setTimeBits(uidProperties.getTimeBits());
        defaultUidGenerator.setWorkerBits(uidProperties.getWorkerBits());
        defaultUidGenerator.setTimeBits(uidProperties.getTimeBits());
        defaultUidGenerator.setEpochStr(uidProperties.getEpochStr());
        return defaultUidGenerator;
    }

    @Bean
    public CachedUidGenerator cachedUidGenerator(UidProperties uidProperties, WorkerIdAssigner workerIdAssigner) {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(workerIdAssigner);
        cachedUidGenerator.setTimeBits(uidProperties.getTimeBits());
        cachedUidGenerator.setWorkerBits(uidProperties.getWorkerBits());
        cachedUidGenerator.setTimeBits(uidProperties.getTimeBits());
        cachedUidGenerator.setEpochStr(uidProperties.getEpochStr());
        return cachedUidGenerator;
    }

    @Bean
    public WorkerIdAssigner workerIdAssigner() {
        WorkerIdAssigner workerIdAssigner = new DisposableWorkerIdAssigner();
        return workerIdAssigner;
    }

    // @Bean
    // public CommandLineRunner commandLineRunner(MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean){
    //     return args -> {
    //         Assert.isNull(mybatisSqlSessionFactoryBean, "未注入mybatisSqlSessionFactoryBean对象");
    //         PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    //         resolver.getResources("META-INF/mybatis/mapper");
    //         mybatisSqlSessionFactoryBean.setMapperLocations();
    //     }
    // }
}