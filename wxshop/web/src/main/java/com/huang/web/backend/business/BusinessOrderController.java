package com.huang.web.backend.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.common.constant.CommonConstant;
import com.huang.common.constant.PageConstant;
import com.huang.common.utils.ResponseResult;
import com.huang.pojo.Business;
import com.huang.pojo.OrderItem;
import com.huang.pojo.Orders;
import com.huang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/backend/business/order")
public class BusinessOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/list")
    public String list(Integer pageNum, Model model, HttpSession session){
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = PageConstant.PAGE_NUM;
        }
        Business business = (Business)session.getAttribute("business");
        PageHelper.startPage(pageNum,PageConstant.PAGE_SIZE);
        List<OrderItem> orderItems = orderService.findForBusiness(business.getId());
        //System.out.println(orderItems);
        PageInfo<OrderItem> pageInfo = new PageInfo<>(orderItems);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("status",-1);
        return "backend/businessOrderManager";
    }

    @RequestMapping("/findByStatus")
    public String findByStatus(Integer pageNum, Integer status,Model model, HttpSession session){
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = PageConstant.PAGE_NUM;
        }
        if (status == -1){
            return "forward:list?pageNum="+pageNum;
        }
        Business business = (Business)session.getAttribute("business");
        PageHelper.startPage(pageNum,PageConstant.PAGE_SIZE);
        List<OrderItem> orderItems = orderService.findForBusinessAndStatus(business.getId(),status);
       // System.out.println(orderItems);
        PageInfo<OrderItem> pageInfo = new PageInfo<>(orderItems);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("status",status);
        return "backend/businessOrderManager";
    }

    //发货,Z状态改为待收货
    @RequestMapping("/send")
    @ResponseBody
    public ResponseResult send(Integer orderItemId, Model model){
        try {
            System.out.println(orderItemId);
            orderService.changeItemStatus(orderItemId, CommonConstant.ORDER_STATUS_UNRECIVED);
            model.addAttribute("msg", "状态修改成功");
            return ResponseResult.success();
        }catch (Exception e){
            model.addAttribute("errorMsg", e.getMessage());
            return ResponseResult.fail();
        }
    }

    @RequestMapping("/toDetails")
    public String findByOderItemId(Integer  id,Model model, HttpSession session){
        OrderItem orderItem = orderService.findByOrderItemId(id);
        Orders order = orderService.findById(orderItem.getOrderId());
        model.addAttribute("orderItem",orderItem);
        model.addAttribute("order",order);
        return "backend/businessOrderDetails";
    }
}
