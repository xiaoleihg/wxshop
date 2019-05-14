package com.huang.dao;

import com.huang.pojo.OrderItem;
import com.huang.pojo.Orders;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderDao")
public interface OrderDao {
    List<Orders> selectAll();
    Orders selectByOrderId(String oid);
    OrderItem selectById(Integer id);
    List<Orders> selectByUserName(String userName);
    List<OrderItem> selectByStatus(Integer status);
    List<OrderItem> selectOrderItemById(String oid);
    List<OrderItem> selectOrderItemByBusinessId(String businessId);
    List<OrderItem> selectOrderItemByUserId(String usersId);
    List<OrderItem> selectOrderItemByBidAndStatus(@Param("businessId")String businessId,@Param("status")Integer status);
    List<OrderItem> selectOrderItemByUidAndStatus(@Param("userId")String businessId,@Param("status")Integer status);
    void updateOrderStatus(@Param("orderId") String oid,@Param("status") Integer status);//用于用户为订单付款，将所有条目设为已付款
    void updateItemStatus(@Param("orderItemId") Integer orderItemId,@Param("status") Integer status);
    void insert(@Param("orders") Orders orders);
}
