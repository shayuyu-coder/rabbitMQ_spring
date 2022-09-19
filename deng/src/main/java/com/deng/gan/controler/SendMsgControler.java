package com.deng.gan.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.deng.gan.config.DelayedQueueConfig.DELAYED_EXCHANGE_NAME;
import static com.deng.gan.config.DelayedQueueConfig.DELAYED_ROUTING_KEY;


/**
 * @author: shayu
 * @date: 2022年09月16日 14:46
 * @ClassName: SendMsgControler
 * @Description:    发送消息的控制层
 */

@Slf4j
@RequestMapping("ttl")
@RestController
public class SendMsgControler {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 普通的信息发送
     * @param message
     */
    @GetMapping("sendMsg/{message}")
    public void sendMsg(@PathVariable String message){

        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 10S 的队列: "+message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl为 40S的队列: "+message);
    }

    /**
     * 有TTL的消息发送
     * @param message
     * @param ttlTime
     */
    @GetMapping("sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime) {
        log.info("当前时间：{},发送一条时长{}毫秒 TTL信息给队列 C:{}",new Date().toString(),ttlTime, message);
        rabbitTemplate.convertAndSend("X", "XC", message, correlationData -> {
            //发送消息时延迟
            correlationData.getMessageProperties().setExpiration(ttlTime);
            return correlationData;
        });
    }

    /**
     * 使用插件的消息发送
     * @param message
     * @param delayTime
     */
    @GetMapping("sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message,@PathVariable Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message,
                correlationData ->{
                    correlationData.getMessageProperties().setDelay(delayTime);
                    return correlationData;
                });
        log.info(" 当 前 时 间 ： {}, 发 送 一 条 延 迟 {} 毫 秒 的 信 息 给 队 列 delayed.queue:{}", new Date(),delayTime, message);
    }
}
