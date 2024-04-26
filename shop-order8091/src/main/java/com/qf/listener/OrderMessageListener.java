package com.qf.listener;

import com.qf.dao.TxLogDao;
import com.qf.pojo.Order;
import com.qf.pojo.TxLog;
import com.qf.service.OrderService4;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RocketMQTransactionListener(txProducerGroup = "tx_order_group")
public class OrderMessageListener implements RocketMQLocalTransactionListener {

    @Autowired
    OrderService4 orderService4;

    @Autowired
    TxLogDao txLogDao;

    //执行本地事务
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            //获取发送的半事务消息
            String txId = (String) message.getHeaders().get("txId");
            orderService4.createOrder(txId,(Order)o);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    //回查本地事务的状态
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String txId = (String) message.getHeaders().get("txId");
        //根据事务号查询对应事务消息
        TxLog txLog = txLogDao.findById(txId).get();
        if(txLog != null){
            return RocketMQLocalTransactionState.COMMIT;
        }else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
