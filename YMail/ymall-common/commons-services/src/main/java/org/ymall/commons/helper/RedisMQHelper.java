package org.ymall.commons.helper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RedisMQHelper
 *
 * @Author IIIDelay
 * @Date 2023/6/15 22:48
 **/
@Component
public class RedisMQHelper implements ApplicationContextAware {
    private StringRedisTemplate redisTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.redisTemplate = applicationContext.getBean(StringRedisTemplate.class);
    }


    /**
     * create by: zz
     * description: 创建消费组
     * create time: 2022/5/11 16:45
     *
     * @return java.lang.String
     * @param:
     */
    public String createGroup(String key, String group) {
        return redisTemplate.opsForStream().createGroup(key, group);
    }

    /**
     * description: 添加Map消息
     *
     * @return
     * @param: key
     * @param: value
     */
    public String addMap(String key, Map<String, String> value) {
        return redisTemplate.opsForStream().add(key, value).getValue();
    }

    /**
     * description: 添加Record消息
     *
     * @return
     * @param: record
     */
    public String addRecord(Record<String, Object> record) {
        return redisTemplate.opsForStream().add(record).getValue();
    }

    /**
     * create by: zz
     * description: 确认消费
     * create time: 2022/5/19 11:21
     *
     * @return java.lang.Long
     * @param: key
     * @param: group
     * @param: recordIds
     */
    public Long ack(String key, String group, String... recordIds) {
        return redisTemplate.opsForStream().acknowledge(key, group, recordIds);
    }

    /**
     * create by: zz
     * description: 删除消息。当一个节点的所有消息都被删除，那么该节点会自动销毁
     * create time: 2022/7/18 15:33
     *
     * @return java.lang.Long
     * @param: key
     * @param: recordIds
     */
    public Long del(String key, String... recordIds) {
        return redisTemplate.opsForStream().delete(key, recordIds);
    }

}
