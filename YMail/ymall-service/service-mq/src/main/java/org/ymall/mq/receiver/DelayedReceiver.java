/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.mq.receiver;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.ymall.mq.config.DelayedMqConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class DelayedReceiver {


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 实现幂等性思路：
     *
     *   1.是否接收消息添加标记
     *     reids setNx
     *   2.如果redis中存在了，一定是消费了吗？
     *       未消费 0
     *       消费  1
     *
     *
     *
     * @param msg
     * @param message
     * @param channel
     */
    @RabbitListener(queues = DelayedMqConfig.queue_delay_1)
    @SneakyThrows
    public void getMessage(String msg, Message message, Channel channel){

        String delayKey="delayed:";

        //设置消息到redis --第一次  true
        Boolean result = redisTemplate.opsForValue().setIfAbsent(delayKey + msg, "0", 10, TimeUnit.MINUTES);
        //判断
        if(!result){

            //判断是否消费
            String flag = (String) this.redisTemplate.opsForValue().get(delayKey + msg);
            //判断
            if("1".equals(flag)){

                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

            }else{


                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

                System.out.println("消息接收的时间：\t"+simpleDateFormat.format(new Date()));



                System.out.println("消息的内容是："+msg);
                //设置消息标识为1
                this.redisTemplate.opsForValue().set(delayKey + msg,"1",10,TimeUnit.MINUTES);

                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

            }



        }else{

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

            System.out.println("消息接收的时间：\t"+simpleDateFormat.format(new Date()));



            System.out.println("消息的内容是："+msg);
            //设置消息标识为1
            this.redisTemplate.opsForValue().set(delayKey + msg,"1",10,TimeUnit.MINUTES);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

        }


    }
}
