package com.huang.web.backend.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.common.constant.PageConstant;
import com.huang.pojo.Business;
import com.huang.pojo.User;
import com.huang.service.BusinessService;
import com.huang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/backend/role")
public class RoleController {

    @Autowired
    private UserService userService;
    @Autowired
    private BusinessService businessServices;

    @RequestMapping("/findAllCustomer")
    public String findAllCustomer(Integer pageNum,Model model){
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = PageConstant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,PageConstant.PAGE_SIZE);
        List<User> userList = userService.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("role","customer");
        return "backend/roleManager";
    }

    @RequestMapping("/findAllBusiness")
    public String findAllBusiness(Integer pageNum,Model model){
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = PageConstant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,PageConstant.PAGE_SIZE);
        List<Business> businessList = businessServices.findAll();
        PageInfo<Business> pageInfo = new PageInfo<>(businessList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("role","business");
        return "backend/roleManager";
    }
}
