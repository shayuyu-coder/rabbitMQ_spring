package com.deng.gan.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: shayu
 * @date: 2022年09月20日 9:52
 * @ClassName: ConfirmConfig
 * @Description:
 *      发布确认高级 配置
 */

@Configuration
public class ConfirmConfig {
    //交换机、队列、routingKey
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    public static final String CONFIRM_ROUTING_KEY = "key1";

    // 备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";
    //备份队列
    public static final String BACKUP_QUEUE_NAME = "backup_queue";
    //报警对列
    public static final String WARNING_QUEUE_NAME = "warning_queue";



    //声明交换机     (并绑定备份交换机)
    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true)
                .withArgument("alternate-exchange",BACKUP_EXCHANGE_NAME).build();
    }
    //队列
    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }
    //绑定
    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue confirmQueue,
                                        @Qualifier("confirmExchange") DirectExchange directExchange){
        return BindingBuilder.bind(confirmQueue).to(directExchange).with(CONFIRM_ROUTING_KEY);
    }


    @Bean("fanoutBackupExchange")
    public FanoutExchange fanoutBackupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }
    @Bean("backupQueue")
    public Queue backupQueue(){
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }
    @Bean("warningQueue")
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }
    //绑定
    @Bean
    public Binding backupQueueBindingBackupExchange(@Qualifier("backupQueue") Queue confirmQueue,
                                        @Qualifier("fanoutBackupExchange") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(confirmQueue).to(fanoutExchange);
    }
    @Bean
    public Binding warningQueuebackupBindingBackupExchange(@Qualifier("warningQueue") Queue confirmQueue,
                                                    @Qualifier("fanoutBackupExchange") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(confirmQueue).to(fanoutExchange);
    }
}
