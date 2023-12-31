/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.mq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ymall.mq.config.DeadLetterMqConfig;
import org.ymall.mq.config.DelayedMqConfig;
import org.ymall.mqs.service.RabbitService;
import org.iiidev.ymall.result.Result;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/mq")
public class MqController {

    @Autowired
    private RabbitService rabbitService;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDelayed")
    public  Result sendDelayed(){
        //时间格式化
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

        //封装后的api
        rabbitService.sendDelayedMessage(DelayedMqConfig.exchange_delay,DelayedMqConfig.routing_delay,"我是延迟插件的消息",10);

        System.out.println("延迟插件消息发送时间：\t"+simpleDateFormat.format(new Date()));
//       this.rabbitTemplate.convertAndSend(DelayedMqConfig.exchange_delay, DelayedMqConfig.routing_delay,
//               "我是延迟插件的消息", new MessagePostProcessor() {
//                   @Override
//                   public Message postProcessMessage(Message message) throws AmqpException {
//
//
//                       //设置消息的延迟时间
//                       message.getMessageProperties().setDelay(10*1000);
//
//                       System.out.println("延迟插件消息发送时间：\t"+simpleDateFormat.format(new Date()));
//
//                       return message;
//                   }
//               });

        return Result.ok();
    }
    /**
     * 发送延迟消息-死信队列
     * @return
     */
   @GetMapping("/sendDeaLetter")
   public Result sendDeaLetter(){


       //时间格式化
       SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

       rabbitService.sendMessage(DeadLetterMqConfig.exchange_dead,DeadLetterMqConfig.routing_dead_1,"我是延迟消息");

       System.out.println("消息发送的时间：\t"+simpleDateFormat.format(new Date()));
       return Result.ok();
   }

    /**
     * 发送消息的方法
     * @return
     */
    @GetMapping("/send")
    public Result send(){

        rabbitService.sendMessage("exchange.confirm888","routingKey.confirm","你好，我是消息，我来了");

        return Result.ok();
    }
}
