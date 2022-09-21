package com.deng.gan.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: shayu
 * @date: 2022年09月21日 9:50
 * @ClassName: MyCallBack
 * @Description:       回调接口，
 *            调用rabbitTemplate里的confirmCallback接口来确认消息
 */
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    /**
     * 完成mycallback的注入
     *
     */
    @Autowired
    RabbitTemplate rabbitTemplate;
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 交换机  确认回调方法
     * @param correlationData   保存回调消息的ID及相关信息
     * @param ack     交换机收到的消息 ack = true成功 ack = fales失败
     * @param cause      cause 成功/失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id=correlationData!=null?correlationData.getId():"";
        if(ack){
            log.info("交换机已经收到 id 为:{}的消息",id);
        }else{
            log.info("交换机还未收到 id 为:{}消息,由于原因:{}",id,cause);
        }

    }

    /**
     *  消息   回调接口
     * @param message   消息
     * @param replyCode     回复代码
     * @param replyText     回复文本
     * @param exchange      交换机
     * @param routingKey    绑定routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

        log.error("消息：{}，被交换机{}退回，退回原因{}，路由key{}",new String(message.getBody()),exchange,replyText,routingKey);

    }
}
