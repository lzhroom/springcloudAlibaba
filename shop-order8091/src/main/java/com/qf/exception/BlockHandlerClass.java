package com.qf.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockHandlerClass {
    public static String blockExceptionHandler(BlockException e){
        log.info("流控异常是{}",e);
        return "当前资源触发了流控规则";
    }
}
