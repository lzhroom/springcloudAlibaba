package com.qf.service.impl;

import com.qf.pojo.SmsSendRecord;
import com.qf.service.SmsService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    RedissonClient redisson;

    public void sendSms(SmsSendRecord smsSendRecord){
        //加分布式锁
        RLock lock = redisson.getLock("sendSms"+ smsSendRecord.getId());
        try {
            lock.lock();
            //短信发送逻辑
        }finally{
            lock.unlock();
        }
    }
}
