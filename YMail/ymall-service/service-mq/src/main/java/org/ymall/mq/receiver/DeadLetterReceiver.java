/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.mq.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.ymall.mq.config.DeadLetterMqConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DeadLetterReceiver {


    @RabbitListener(queues = DeadLetterMqConfig.queue_dead_2)
    public void getMessage(String msg, Message message, Channel channel){

        //时间格式化
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

        System.out.println("消息接收的时间：\t"+simpleDateFormat.format(new Date()));

        System.out.println("消息的内容"+msg);

    }
}
