package com.qf.dao;

import com.qf.pojo.SmsSendRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface smsSendRecordRepo extends JpaRepository<SmsSendRecord,String> {
}
