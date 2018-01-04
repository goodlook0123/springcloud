package com.springbootclient.MQ;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
@Component
public class Receiver {

    @RabbitListener(queues = "kunfind")
    public void process(String str){
        System.out.println("receiver:"+str);
    }
}
