package com.qf.service.impl;

import com.qf.dao.OrderDao;
import com.qf.pojo.Order;
import com.qf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }
}
