package com.huang.service;

import com.huang.pojo.OrderItem;
import com.huang.pojo.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderService {
    void createOrder(Orders orders);
    void changeOrderStatus(String oid,Integer status);
    void changeItemStatus(Integer itemId,Integer status);
    List<Orders> findAll();
    List<OrderItem> findDetails(String oid);//查询订单明细
    Orders findById(String oid);
    OrderItem findByOrderItemId(Integer id);
    List<OrderItem> findForBusiness(String businessId);//店家查找有自己商品的订单条目
    List<OrderItem> findForCustomer(String userId);//店家查找有自己商品的订单条目

    List<OrderItem> findForBusinessAndStatus(String businessId,Integer status);
    List<OrderItem> findForCustomerAndStatus(String userId,Integer status);
}
