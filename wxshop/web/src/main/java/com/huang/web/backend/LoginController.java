package com.huang.web.backend;

import com.huang.common.utils.MD5Utils;
import com.huang.pojo.Business;
import com.huang.pojo.Manager;
import com.huang.service.BusinessService;
import com.huang.service.ManagerService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;


@Controller("managerController")
@RequestMapping("/backend")
public class LoginController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private BusinessService businessService;

    @RequestMapping("/to_login")
    public String toLogin(){

        return "backend/login";
    }
    @RequestMapping("/login")
    public String login(HttpSession session,Model model,String name, String password ,Integer role)throws Exception{
        //System.out.println(name+password+role);
        //1.判断用户名和密码是否为空
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            model.addAttribute("msg","用户名和密码不能为空");
            return "forward:to_login";
        }
        //2.判断是否勾选用户类型
        if(role==null){
           model.addAttribute("msg","请选择用户类型");
           return "forward:to_login";
        }else if(role == 0){//管理员
            Manager manager = managerService.find(name, MD5Utils.getMD5Str(password));
            if(manager == null){
                model.addAttribute("msg","账号或密码错误");
                return "forward:to_login";
            }
            session.setAttribute("manager",manager);
            return "redirect:manager/to_main";
        }else if (role == 1){//店家
            Business business = businessService.queryBusinessForLogin(name,MD5Utils.getMD5Str(password));
            //System.out.println(business);
            if(business == null){
                model.addAttribute("msg","账号或密码错误");
                return "forward:to_login";
            }
            session.setAttribute("business",business);
            return "redirect:business/to_main";
        }

        return "redirect:to_login";
    }
    @RequestMapping("/manager/to_main")
    public String toMain(){
        return "backend/main";
    }

    @RequestMapping("/business/to_main")
    public String toBusinessMain(){
        return "backend/businessMain";
    }

    @RequestMapping("/to_regist")
    public String toRegist(){
        return "backend/businessRegist";
    }

    @RequestMapping("/regist")
    public String regist(HttpSession session,Model model,String name, String password)throws Exception {
        System.out.println(name+password);
        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            model.addAttribute("msg", "注册失败，账号和密码必须不为空");
            return "forward:to_regist";
        }
        // 2. 判断用户名是否存在
        boolean businessNameIsExist = businessService.queryBusinessIsExist(name);
        // 3. 保存用户，注册信息
        if (!businessNameIsExist) {
            Business business = new Business();
            business.setId(UUID.randomUUID().toString());
            business.setName(name);
            business.setPassword(MD5Utils.getMD5Str(password));
            business.setShopName(name+"的店");
            businessService.saveBusiness(business);
            //去掉密码，并将其保存到session
            business.setPassword("");
            session.setAttribute("business",business);
        } else {
            model.addAttribute("msg", "注册失败，该账号已存在");
            return "forward:to_regist";
        }
        return "redirect:business/to_main";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        if(session.getAttribute("business") != null){
            session.removeAttribute("business");
        }
        if(session.getAttribute("manager") != null){
            session.removeAttribute("manager");
        }
        return "redirect:to_login";
    }
}
