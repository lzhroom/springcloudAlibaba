package com.qf.controller;

import com.qf.dao.smsSendRecordRepo;
import com.qf.pojo.SmsSendRecord;
import com.qf.service.SmsService;
import com.xiaoleilu.hutool.util.RandomUtil;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.concurrent.TimeUnit;

@RestController
public class SendSmsController {

    @Autowired
    SmsService smsService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    smsSendRecordRepo smsSendRecordRepo;

    @RequestMapping("/sendsms")
    public void sendSmsVerificationCode(@NotBlank String mobile, @NotBlank String type) {
            String limitKey = mobile+type;
            String smsSended = String.valueOf(redisTemplate.opsForValue().get(limitKey));
            if (StringUtil.isNotBlank(smsSended)){
                throw new RuntimeException("请勿频繁发送验证码");
            }
            //记录手机号发送过验证码，记录1分钟
            redisTemplate.opsForValue().set(limitKey, "1",60, TimeUnit.SECONDS);
            //使用hutool的工具生成四位随机验证码
            String verificationCode = RandomUtil.randomString(4);
            //把验证码存在Redis中
            redisTemplate.opsForValue().set(type + mobile, verificationCode, 60 * 5,TimeUnit.SECONDS);
            //构建一条发送记录
            SmsSendRecord smsSendRecord = new SmsSendRecord(mobile, type, verificationCode);
            //落库保存
            smsSendRecordRepo.save(smsSendRecord);
            //发送短信验证码
            smsService.sendSms(smsSendRecord);
    }
}
