package com.qf.controller;

import com.alibaba.fastjson.JSON;
import com.qf.feign.ProductFeignClient;
import com.qf.pojo.Order;
import com.qf.pojo.Product;
import com.qf.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@SuppressWarnings("all")
public class OrderController {

    /*@Autowired
    RestTemplate restTemplate;*/

    @Autowired
    OrderService orderService;

    @Autowired
    ProductFeignClient productFeignClient;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    //买1件商品
    @RequestMapping("/order/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid){
        //通过restTemplate调用商品微服务
        //Product product = restTemplate.getForObject("http://localhost:8081/product/" + pid, Product.class);
        //根据服务实例名称调用
        //Product product = restTemplate.getForObject("http://service-product/product/" + pid, Product.class);
        Product product = productFeignClient.product(pid);
        log.info("商品信息,查询结果:" + JSON.toJSONString(product));
        //生成订单，并持久化订单
        Order order = new Order();
        order.setUid(1);
        order.setUsername("测试用户");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        //持久化订单信息
        orderService.save(order);
        //下单成功后，将消息放到mq中
        rocketMQTemplate.convertAndSend("order-topic",order);
        return order;
    }

    @RequestMapping("order/message")
    public String getMessage(){
        String message = productFeignClient.message();
        return message;
    }

}
