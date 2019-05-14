package com.huang.web.backend.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.common.constant.PageConstant;
import com.huang.pojo.Goods;
import com.huang.pojo.OrderItem;
import com.huang.pojo.Orders;
import com.huang.service.GoodsService;
import com.huang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/backend/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/list")
    public String list(Integer pageNum, Model model){
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = PageConstant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,PageConstant.PAGE_SIZE);
        List<Orders> orderList = orderService.findAll();
        //System.out.println(orderList);
        PageInfo<Orders> pageInfo = new PageInfo<>(orderList);
        model.addAttribute("pageInfo",pageInfo);
        return "backend/orderManager";
    }
    @RequestMapping("/toDetails")
    public String toDetails(String id,Model model){
        List<OrderItem> orderItems = orderService.findDetails(id);
        Orders order = orderService.findById(id);
        model.addAttribute("orderItems",orderItems);
        model.addAttribute("order",order);
        return "backend/orderDetails";
    }
}
