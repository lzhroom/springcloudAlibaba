package com.qf.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandlerClass {
    public static String fallbackExceptionHandler(Throwable e){
        log.info("运行时异常是{}",e);
        return "当前资源内部出现了异常";
    }
}
