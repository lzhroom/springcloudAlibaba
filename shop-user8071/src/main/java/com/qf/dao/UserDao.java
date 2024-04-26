package com.qf.dao;

import com.qf.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {//User 实体类类型  Integer 主键id的数据类型
}
