package com.qf.controller;

import com.alibaba.fastjson.JSON;
import com.qf.feign.ProductFeignClient;
import com.qf.pojo.Order;
import com.qf.pojo.Product;
import com.qf.service.OrderService4;
import com.qf.service.impl.OrderServiceImpl4;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@SuppressWarnings("all")
public class OrderController4 {

    @Autowired
    ProductFeignClient feignClient;

    @Autowired
    OrderService4 orderService4;

    //买1件商品
    @RequestMapping("/order4/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid){
        Product product =feignClient.product(pid);
        log.info("商品信息,查询结果:" + JSON.toJSONString(product));

        //生成订单，并持久化订单
        Order order = new Order();
        order.setUid(1);
        order.setUsername("发送mq消息");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        //发送半事务消息
        orderService4.createOrderBefore(order);
        return order;
    }
}
