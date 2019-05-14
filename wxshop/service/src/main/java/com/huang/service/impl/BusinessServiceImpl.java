package com.huang.service.impl;

import com.huang.dao.BusinessDao;
import com.huang.pojo.Business;
import com.huang.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("businessService")
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private BusinessDao businessDao;
    @Override
    public boolean queryBusinessIsExist(String name) {
        Business business = businessDao.selectByName(name);
        return business == null?false:true;
    }

    @Override
    public void saveBusiness(Business business) {
        businessDao.insert(business);
    }

    @Override
    public Business queryBusinessForLogin(String name, String password) {
        return businessDao.selectByNameAndPwd(name,password);
    }

    @Override
    public void updateBusinessInfo(Business business) {
        businessDao.update(business);
    }

    @Override
    public Business queryBusinessInfo(String businessId) {
        return businessDao.selectById(businessId);
    }

    @Override
    public Business queryShopNameIsExist(String shopName) {

        return businessDao.selectByShopName(shopName);
    }

    @Override
    public List<Business> findAll() {
        return businessDao.selectAll();
    }
}
