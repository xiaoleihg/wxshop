package com.huang.service.impl;

import com.huang.dao.ManagerDao;
import com.huang.pojo.Manager;
import com.huang.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDao managerDao;

    @Override
    public Manager find(String managerName, String password) {
        return managerDao.select(managerName,password);
    }

}
