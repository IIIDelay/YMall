/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.activity.redis;

import org.springframework.stereotype.Component;
import org.ymall.activity.util.CacheHelper;

@Component
public class MessageReceive {


    public  void receiveMessage(String message){


        System.out.println(message);
        //去掉双引号
        message=  message.replaceAll("\"","");
        //截取
        String[] split = message.split(":");
        //判断
        if(split!=null&&split.length==2){

            CacheHelper.put(split[0],split[1]);

        }

    }
}
