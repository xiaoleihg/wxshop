package com.huang.dao;

import com.huang.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public interface UserDao {
    User selectById(String id);
    User selectByName(String name);
    User selectByNameAndPwd(@Param("name") String name, @Param("password") String password);
    void update(User user);
    void insert(User user);
    List<User> selectAll();
}
