package com.deng.gan.controler;

import com.deng.gan.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author: shayu
 * @date: 2022年09月16日 15:36
 * @ClassName: Consumer01
 * @Description:
 */

public class Consumer01 {

    //交换机
    public static final String X_EXCHANGE = "X";
    //队列
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";

    public static void main(String[] args) throws  Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //声明交换机  普通
//        channel.exchangeDeclare(X_EXCHANGE, BuiltinExchangeType.DIRECT);

        /**
         * 生成一个队列(queueDeclare参数解析)
         * 1.队列名称
         * 2.队列里面的消息是否持久化 默认消息存储在内存中
         * 3.该队列是否只供一个消费者进行消费 是否进行共享 true可以多个消费者消费
         * 4.是否自动删除 最后一个消费者端开连接以后 该队列是否自动删除 true 自动删除
         *          5.其他参数（死信交换机链接和设置）
         */
//        channel.queueDeclare(QUEUE_A,false,false,true,null);
//        channel.queueDeclare(QUEUE_B,false,false,true,null);

        //绑定交换机和队列
        channel.queueBind(QUEUE_A,X_EXCHANGE,"XA");
        channel.queueBind(QUEUE_B,X_EXCHANGE,"XB");
        System.out.println("等待接受消息。。。。。");

        DeliverCallback deliverCallbackA=(consumerTag, delivery)->{
            String message= new String(delivery.getBody());
            System.out.println("ConsumerA接受的消息是：" + message);
        };
        DeliverCallback deliverCallbackB=(consumerTag, delivery)->{
            String message= new String(delivery.getBody());
            System.out.println("ConsumerB接受的消息是：" + message);
        };
        channel.basicConsume(QUEUE_A,true, deliverCallbackA,consumerTag->{});
        channel.basicConsume(QUEUE_B,true, deliverCallbackB,consumerTag->{});

    }
}
