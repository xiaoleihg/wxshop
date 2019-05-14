package com.huang.dao;

import com.huang.pojo.Manager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("managerDao")
public interface ManagerDao {
    Manager select(@Param("name") String managerName, @Param("password") String password);
    void insert(Manager manager);
}
