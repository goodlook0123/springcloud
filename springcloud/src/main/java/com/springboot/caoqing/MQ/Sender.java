package com.springboot.caoqing.MQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String context){
        //String context = "hello ,welcome to kunfind    ^_^";
        amqpTemplate.convertAndSend("kunfind",context);
    }

}
