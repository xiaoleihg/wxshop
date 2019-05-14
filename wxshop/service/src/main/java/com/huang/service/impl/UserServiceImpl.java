package com.huang.service.impl;

import com.huang.dao.UserDao;
import com.huang.pojo.User;
import com.huang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public boolean queryUsernameIsExist(String username) {
        User user = userDao.selectByName(username);
        return user== null ? false:true;
    }

    @Override
    public void saveUser(User user) {
        userDao.insert(user);
    }

    @Override
    public User queryUserForLogin(String username, String password) {
        return userDao.selectByNameAndPwd(username, password);
    }

    @Override
    public void updateUserInfo(User user) {
        userDao.update(user);
    }

    @Override
    public User queryUserInfo(String userId) {
        return userDao.selectById(userId);
    }

    @Override
    public List<User> findAll() {
        return userDao.selectAll();
    }
}
