package com.qf.controller;

import com.qf.pojo.Order;
import com.qf.service.OrderService5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@SuppressWarnings("all")
public class OrderController5 {

    @Autowired
    OrderService5 orderService5;

    @RequestMapping("/order5/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid) {
        return orderService5.createOrder(pid);
    }
}
