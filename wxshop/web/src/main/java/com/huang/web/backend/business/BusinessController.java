package com.huang.web.backend.business;

import com.huang.pojo.Business;
import com.huang.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/backend/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @RequestMapping("/find")
    public String find(){
        return "backend/businessInfo";
    }
    @RequestMapping("/change")
    public String change(Business business, Model model,HttpSession httpSession){
        //1.账号不允许修改的，所以先检查店名是否存在
        Business business1 = businessService.queryShopNameIsExist(business.getShopName());
        if(business1 != null && !business.getId().equals(business1.getId())){
            model.addAttribute("msg","该店名已存在，换一个试试吧！！");
            return "forward:find";
        }
        //输入店名且店名不存在，这是第2次修改。将店名置为null
        if (business1 == null && business.getVersion()==2 && business.getShopName() != null){
            model.addAttribute("msg","店名只能修改一次");
            business.setShopName(null);
        }
        //输入店名且店名不存在，这是第一次修改。将version置为2
        if (business1 == null && business.getVersion()==1 && business.getShopName() != null){
            business.setVersion(2);
        }
        //2.更新session中的数据
        businessService.updateBusinessInfo(business);
        model.addAttribute("msg","信息修改成功");
        Business business2 = businessService.queryBusinessInfo(business.getId());
        httpSession.setAttribute("business",business2);
        return "forward:find";
    }
}
