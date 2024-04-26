package com.qf.pojo;

import lombok.Data;

@Data
public class SmsSendRecord {

    private String mobile;

    private String type;

    private String verificationCode;

    public SmsSendRecord(String mobile, String type, String verificationCode) {
    }

    private String id;
}
