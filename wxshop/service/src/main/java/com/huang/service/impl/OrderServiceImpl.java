package com.huang.service.impl;

import com.huang.dao.OrderDao;
import com.huang.pojo.OrderItem;
import com.huang.pojo.Orders;
import com.huang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public void createOrder(Orders orders) {
        orderDao.insert(orders);
    }

    @Override
    public void changeOrderStatus(String oid, Integer status) {
        orderDao.updateOrderStatus(oid, status);
    }

    @Override
    public void changeItemStatus(Integer itemId, Integer status) {
        orderDao.updateItemStatus(itemId,status);
    }

    @Override
    public List<Orders> findAll() {
        return orderDao.selectAll();
    }

    @Override
    public List<OrderItem> findDetails(String oid) {
        return orderDao.selectOrderItemById(oid);
    }

    @Override
    public Orders findById(String oid) {
        return orderDao.selectByOrderId(oid);
    }

    @Override
    public OrderItem findByOrderItemId(Integer id) {
        return orderDao.selectById(id);
    }

    @Override
    public List<OrderItem> findForBusiness(String businessId) {
        return orderDao.selectOrderItemByBusinessId(businessId);
    }

    @Override
    public List<OrderItem> findForCustomer(String userId) {
        return orderDao.selectOrderItemByUserId(userId);
    }

    @Override
    public List<OrderItem> findForBusinessAndStatus(String businessId, Integer status) {
        return orderDao.selectOrderItemByBidAndStatus(businessId,status);
    }

    @Override
    public List<OrderItem> findForCustomerAndStatus(String userId, Integer status) {
        return orderDao.selectOrderItemByUidAndStatus(userId,status);
    }
}
