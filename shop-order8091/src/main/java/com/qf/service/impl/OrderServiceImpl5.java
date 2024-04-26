package com.qf.service.impl;

import com.qf.dao.OrderDao;
import com.qf.feign.ProductFeignClient;
import com.qf.pojo.Order;
import com.qf.pojo.Product;
import com.qf.service.OrderService5;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@SuppressWarnings("all")
public class OrderServiceImpl5 implements OrderService5 {

    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @GlobalTransactional
    public Order createOrder(Integer pid) {
        //1 调用商品微服务,查询商品信息
        Product product = productFeignClient.product(pid);
        //2 下单(创建订单)
        Order order = new Order();
        order.setUid(5);
        order.setUsername("测试Seata");
        order.setPid(pid);
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderDao.save(order);
        //3 扣库存m
        productFeignClient.reduceInventory(pid, order.getNumber());
        return order;
    }
}
