package com.qf.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.qf.service.SentinelOrderService;
import org.springframework.stereotype.Service;

@Service
public class SentinelOrderServiceImpl implements SentinelOrderService {

    @SentinelResource("message")//指定资源名称
    @Override
    public String message() {
        return "Message";
    }
}
