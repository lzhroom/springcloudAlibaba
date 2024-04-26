package com.qf.listener;

import com.alibaba.fastjson.JSON;
import com.qf.pojo.Order;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 消费消息的监听器
 */
@Component
@RocketMQMessageListener(consumerGroup = "message-group",topic = "order-topic")
public class MessageConsumerListener implements RocketMQListener<Order> {

    @Override
    public void onMessage(Order order) {
        System.out.println("用户微服务监听到了生产者发送过来的消息，消息是:" + JSON.toJSONString(order));
    }
}
