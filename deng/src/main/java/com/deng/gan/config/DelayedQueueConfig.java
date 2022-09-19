package com.deng.gan.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shayu
 * @date: 2022年09月19日 14:06
 * @ClassName: DelayedQueueConfig
 * @Description:    延迟插件config配置
 */

@Configuration
public class DelayedQueueConfig {
    //队列
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    //交换机
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    //routingKey 绑定
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";


    //自定义交换机 我们在这里定义的是一个延迟交换机
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        //自定义交换机的类型 (这里的类型应该是指发送消息时的类型，和使用插件的类型不一样)
        args.put("x-delayed-type", "direct");
        /**
         * 自定义交换机参数
         * 1.交换机名称
         * 2.交换机插件类型名称
         * 3.是否需要持久化
         * 4.是否自动删除
         * 5.其他参数
         * （可以自己点进去看看，这里的类型和上面的不冲突）
         */
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }
    //声明队列
    @Bean
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE_NAME);
    }
    //声明routingKey 绑定关系
    @Bean
    public Binding bindingDelayedQueue(@Qualifier("delayedQueue") Queue queue,
                                       @Qualifier("delayedExchange")
                                               CustomExchange
                                               delayedExchange) {
        return BindingBuilder.bind(queue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}
