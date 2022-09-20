package com.deng.gan.consumer;

import com.deng.gan.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * @author: DengGanWen
 * @date: 2022年09月20日 16:48
 * @ClassName: ConfirmConsumer
 * @Description:    发布确认高级  监听
 */

@Slf4j
@Component
public class ConfirmConsumer {
    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveMsg(Message message){
        String msg = new String(message.getBody());
        log.info("接受到队列 confirm.queue消息:{}",msg);
    }
}
