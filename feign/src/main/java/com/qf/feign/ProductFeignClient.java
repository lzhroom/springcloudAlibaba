package com.qf.feign;

import com.qf.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-product"/*,fallback = ProductFallBackClass.class*/)
public interface ProductFeignClient {

    @RequestMapping("product/{pid}")
    Product product(@PathVariable("pid") Integer pid);

    @RequestMapping("/order/message")
    String message();

    //扣减库存
    //参数一: 商品标识
    //参数二:扣减数量
    @RequestMapping("/product/reduceInventory")
    void reduceInventory(@RequestParam("pid") Integer pid,
                         @RequestParam("number") Integer number);

}
