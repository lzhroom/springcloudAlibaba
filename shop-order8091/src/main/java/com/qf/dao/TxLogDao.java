package com.qf.dao;

import com.qf.pojo.TxLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxLogDao extends JpaRepository<TxLog,String> {
}
