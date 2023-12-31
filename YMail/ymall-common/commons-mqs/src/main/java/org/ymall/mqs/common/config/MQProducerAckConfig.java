/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.mqs.common.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.ymall.mqs.model.GmallCorrelationData;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MQProducerAckConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);

    }

    /**
     * 确认消息是否到达交换机
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送成功：" + JSON.toJSONString(correlationData));
        } else {
            log.info("消息发送失败：" + cause + " 数据：" + JSON.toJSONString(correlationData));
            this.retryMessage(correlationData);
        }
    }

    /**
     * 确实消息是否正确到达队列
     * 执行时机：
     * 到达不执行
     * 没到执行
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

        System.out.println("消息主体: " + new String(message.getBody()));
        System.out.println("应答码: " + replyCode);
        System.out.println("描述：" + replyText);
        System.out.println("消息使用的交换器 exchange : " + exchange);
        System.out.println("消息使用的路由键 routing : " + routingKey);

        // 从redis中获取数据
        String correlationDataId = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
        if (!StringUtils.isEmpty(correlationDataId)) {

            // 从redis中获取数据
            String strJson = (String) redisTemplate.opsForValue().get(correlationDataId);

            GmallCorrelationData gmallCorrelationData = JSON.parseObject(strJson, GmallCorrelationData.class);

            // 调用重试方法
            this.retryMessage(gmallCorrelationData);

        }


    }

    /**
     * 实现重试方法
     *
     * @param correlationData
     */
    private void retryMessage(CorrelationData correlationData) {

        // 转换
        GmallCorrelationData gmallCorrelationData = (GmallCorrelationData) correlationData;
        // 获取重试次数
        int retryCount = gmallCorrelationData.getRetryCount();
        // 判断
        if (retryCount > 3) {

            System.out.println("重试次数已到，结束吧");
        } else {
            retryCount += 1;
            gmallCorrelationData.setRetryCount(retryCount);
            // 更新redis
            redisTemplate.opsForValue().set(gmallCorrelationData.getId(), JSON.toJSONString(gmallCorrelationData));
            System.out.println("重试次数：\t" + retryCount);
            // 判断是否延迟
            if (gmallCorrelationData.isDelay()) {

                this.rabbitTemplate.convertAndSend(gmallCorrelationData.getExchange(), gmallCorrelationData.getRoutingKey(), gmallCorrelationData.getMessage(), message -> {

                    message.getMessageProperties().setDelay(gmallCorrelationData.getDelayTime() * 1000);
                    return message;
                }, gmallCorrelationData);

            } else {

                // 重新发送
                this.rabbitTemplate.convertAndSend(gmallCorrelationData.getExchange(), gmallCorrelationData.getRoutingKey(), gmallCorrelationData.getMessage(), gmallCorrelationData);

            }


        }


    }

}
