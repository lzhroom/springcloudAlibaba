package com.qf.service.impl;

import com.qf.dao.OrderDao;
import com.qf.dao.TxLogDao;
import com.qf.pojo.Order;
import com.qf.pojo.TxLog;
import com.qf.service.OrderService4;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@SuppressWarnings("all")
public class OrderServiceImpl4 implements OrderService4 {

    @Autowired
    OrderDao orderDao;

    @Autowired
    TxLogDao txLogDao;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    //发送半事务消息
    public void createOrderBefore(Order order){
        //生成事务号
        String txId = UUID.randomUUID().toString();
        //发送消息 参数1 生产组 参数2 发送到topic名称 参数3 消息的主题  参数4 额外的参数
        rocketMQTemplate.sendMessageInTransaction("tx_order_group",
                "tx_order_topic",
                MessageBuilder.withPayload(order).setHeader("txId", txId).build(),
                order
                );
    }

    //本地事务
    @Transactional
    public void createOrder(String txId, Order order) {
        //保存订单
        orderDao.save(order);
        TxLog txLog = new TxLog();
        txLog.setTxId(txId);
        txLog.setDate(new Date());
        //记录事物日志
        txLogDao.save(txLog);
    }
}
