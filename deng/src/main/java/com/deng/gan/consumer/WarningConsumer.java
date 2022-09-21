package com.deng.gan.consumer;

import com.deng.gan.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: shayu
 * @date: 2022年09月21日 14:07
 * @ClassName: WarningConsumer
 * @Description:      备份交换机消费者 （监听）
 */

@Slf4j
@Component
public class WarningConsumer {
    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void warningMsg(Message message){
        String msg = new String(message.getBody());
        log.info("报警发现不可路由消息:{}",msg);
    }
}
