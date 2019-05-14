package com.huang.service;


import com.huang.pojo.Manager;


public interface ManagerService {
    Manager find(String managerName, String password);
}
