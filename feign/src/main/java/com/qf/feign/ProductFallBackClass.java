package com.qf.feign;

import com.qf.pojo.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductFallBackClass implements ProductFeignClient{
    @Override
    public Product product(Integer pid) {
        Product product = new Product();
        product.setPid(-1);
        product.setPname("远程调用商品微服务逻辑失败了，执行容错逻辑");
        return product;
    }

    @Override
    public String message() {
        return "触发了降级，执行兜底方法";
    }

    @Override
    public void reduceInventory(Integer pid, Integer number) {

    }
}
