package com.qf.service;

import com.qf.pojo.Order;

public interface OrderService4 {
    void createOrderBefore(Order order);
    void createOrder(String txId, Order order);
}
