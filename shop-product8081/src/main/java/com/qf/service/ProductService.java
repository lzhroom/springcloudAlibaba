package com.qf.service;

import com.qf.pojo.Product;

public interface ProductService {
    //根据id查询商品
    Product findByPid(Integer pid);

    void reduceInventory(Integer pid, Integer number);
}
