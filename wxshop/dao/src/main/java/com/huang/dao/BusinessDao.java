package com.huang.dao;

import com.huang.pojo.Business;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("businessDao")
public interface BusinessDao {
    Business selectById(String id);
    Business selectByName(String name);
    Business selectByShopName(String shopName);
    Business selectByNameAndPwd(@Param("name") String name, @Param("password") String password);
    void update(Business business);
    void insert(Business business);

    List<Business> selectAll();
}
