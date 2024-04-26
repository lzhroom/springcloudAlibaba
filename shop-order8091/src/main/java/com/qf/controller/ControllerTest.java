package com.qf.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.qf.exception.BlockHandlerClass;
import com.qf.exception.ExceptionHandlerClass;
import com.qf.service.SentinelOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ControllerTest {

    @Autowired
    SentinelOrderService sentinelOrderService;

    @RequestMapping("/order/message1")
    public String message1() {
        return sentinelOrderService.message();
    }

    int i = 0;
    @RequestMapping("/order/message2")
    @SentinelResource(
            value = "message2",
            blockHandlerClass = BlockHandlerClass.class,
            //触发流控之后，需要执行的兜底方法
            blockHandler = "blockExceptionHandler",
            fallbackClass = ExceptionHandlerClass.class,
            //触发Throwable类型异常需要执行的方法
            fallback = "fallbackExceptionHandler"
    )
    public String message2() {
        i++;
        if(i%3==0){
            throw new RuntimeException("余数不能为0");
        }
        return sentinelOrderService.message();
    }

    @RequestMapping("/order/message3")
    @SentinelResource("message3")
    public String message3(String name){
        return name;
    }

    /*public String blockExceptionHandler(BlockException e){
        log.info("流控异常是{}",e);
        return "当前资源触发了流控规则";
    }*/

    /*public String fallbackExceptionHandler(Throwable e){
        log.info("运行时异常是{}",e);
        return "当前资源内部出现了异常";
    }*/

}
