package com.deng.gan.controler;

import com.deng.gan.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: shayu
 * @date: 2022年09月20日 10:07
 * @ClassName: ProducerControler
 * @Description:        发布确认高级发消息
 */
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProducerControler {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //正常发送消息
    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message){
        CorrelationData id = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY,message,id);
        log.info("发送消息内容：{}",message);
    }
    //交换机宕机发送消息
    @GetMapping("/sendMessageExchange/{message}")
    public void sendMessageExchange(@PathVariable String message){
        CorrelationData id = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME+ "A",ConfirmConfig.CONFIRM_ROUTING_KEY,message,id);
        log.info("发送消息内容：{}",message);
    }
    //交换机宕机发送消息
    @GetMapping("/sendMessageQueue/{message}")
    public void sendMessageQueue(@PathVariable String message){
        CorrelationData id = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY+ "A",message,id);
        log.info("发送消息内容：{}",message);
    }
}
