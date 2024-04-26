package com.qf.controller;

import com.alibaba.fastjson.JSON;
import com.qf.pojo.Product;
import com.qf.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RefreshScope
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("product/{pid}")
    public Product product(@PathVariable("pid") Integer pid){
        try {
            Product product = productService.findByPid(pid);
            log.info("查询到商品:" + JSON.toJSONString(product));
            //Thread.sleep(3000);
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/order/message")
    public String message(){
        return"高并发下的问题测试";
    }

    /*@Value("${product.name}")
    String productName;

    @RequestMapping("product/name")
    public String getProductName(){
        return productName;
    }*/

    @RequestMapping("/product/reduceInventory")
    public void reduceInventory(@RequestParam("pid") Integer pid, @RequestParam("number") Integer number){
        //根据商品ID查询对应的商品信息
        productService.reduceInventory(pid,number);

    }
}
