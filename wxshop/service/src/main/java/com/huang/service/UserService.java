package com.huang.service;

import com.huang.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * @Description: 判断用户名是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * @Description: 保存用户(用户注册)
     */
    void saveUser(User user);

    /**
     * @Description: 用户登录，根据用户名和密码查询用户
     */
    User queryUserForLogin(String username, String password);

    /**
     * @Description: 用户修改信息
     */
    void updateUserInfo(User user);

    /**
     * @Description: 查询用户信息
     */
    public User queryUserInfo(String userId);
    /**
     * @Description: 查询所有用户信息
     */
    public List<User> findAll();
}
