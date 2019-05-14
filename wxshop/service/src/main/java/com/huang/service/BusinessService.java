package com.huang.service;

import com.huang.pojo.Business;

import java.util.List;

public interface BusinessService {
    /**
     * @Description: 判断用户名是否存在
     */
    boolean queryBusinessIsExist(String name);

    /**
     * @Description: 保存用户(用户注册)
     */
    void saveBusiness(Business business);

    /**
     * @Description: 用户登录，根据用户名和密码查询用户
     */
    Business queryBusinessForLogin(String name, String password);

    /**
     * @Description: 用户修改信息
     */
    void updateBusinessInfo(Business business);

    /**
     * @Description: 查询用户信息
     */
    public Business queryBusinessInfo(String businessId);

    /**
     * @Description: 查询店铺名称是否已经存在
     */
    public Business queryShopNameIsExist(String shopName);
    /**
     * @Description: 查询所有店家
     */
    public List<Business> findAll();
}
