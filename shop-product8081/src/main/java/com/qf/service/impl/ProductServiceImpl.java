package com.qf.service.impl;

import com.qf.dao.ProductDao;
import com.qf.pojo.Product;
import com.qf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public Product findByPid(Integer pid) {
        Optional<Product> optional = productDao.findById(pid);
        return optional.get();
    }

    @Override
    public void reduceInventory(Integer pid, Integer number) {
        Product product = productDao.findById(pid).get();
        if(product.getStock()<number){
            throw new RuntimeException("库存不足");
        }
        int i = 1/0;
        product.setStock(product.getStock()-number);
        //将重新设置的库存信息保存在数据表里面
        productDao.save(product);
    }
}
