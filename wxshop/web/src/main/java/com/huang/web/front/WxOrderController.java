package com.huang.web.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huang.common.constant.CommonConstant;
import com.huang.common.utils.ResponseResult;
import com.huang.pojo.*;
import com.huang.service.GoodsService;
import com.huang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController("wxOrderController")
@RequestMapping("/wxOrder")
public class WxOrderController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @PostMapping("/createOrder")
    public ResponseResult createOrder(@RequestBody String data){
        JSONObject jsonObject = JSONObject.parseObject(data);
        User user = jsonObject.getObject("user", User.class);
        Orders order = new Orders();//创建订单对象并完善
        order.setOid(UUID.randomUUID().toString());
        order.setCreateTime(new Date());
        order.setUser(user);
        order.setTotal(jsonObject.getDouble("total"));
        order.setAddress(jsonObject.getString("address"));
        /*获取订单条目列表
        * 1.获得购物车条目；2.创建订单条目列表； 3.通过foreach将cartItem转化为orderItem
        * */
        JSONArray jsonArray = jsonObject.getJSONArray("orderItems");
        List<CartItem> cartItems = jsonArray.toJavaList(CartItem.class);
        System.out.println(cartItems);
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem:cartItems) {
            Goods goods1 = goodsService.findById(cartItem.getGid());
            System.out.println(goods1);
            if(goods1.getIsValid()==0) {
                return ResponseResult.fail("抱歉"+cartItem.getGoodsName() + "已下架");
            }else if(goods1.getStock()<cartItem.getCount()){
               return ResponseResult.fail("抱歉"+cartItem.getGoodsName()+"库存不足");
            }else if(goods1.getBusiness().getStatus()==2){
                return ResponseResult.fail("抱歉商家应违规操作被封号");
            }else{
                OrderItem orderItem = new OrderItem();
                orderItem.setGoodsId(goods1.getGid());
                orderItem.setStatus(CommonConstant.ORDER_STATUS_UNPAID);
                orderItem.setCount(cartItem.getCount());
                orderItem.setSubtotal(cartItem.getPrice()*cartItem.getCount());
                orderItems.add(orderItem);
            }
        }
        //完善订单对象，设置订单条目，持久化到数据库，并减少对应商品的库存
        order.setOrderItemList(orderItems);
        orderService.createOrder(order);
        goodsService.decreaseStock(orderItems);
        order.setOrderItemList(null);
        return ResponseResult.success(order);
    }

    @PostMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody String data){
        JSONObject jsonObject = JSONObject.parseObject(data);
        String oid = jsonObject.getString("oid");
        Integer status = jsonObject.getInteger("status");
        orderService.changeOrderStatus(oid,status);
        return ResponseResult.success();
    }

    @PostMapping("/findByStatus")
    public ResponseResult findByStatus(@RequestBody String data){
        JSONObject jsonObject = JSONObject.parseObject(data);
        String userId = jsonObject.getString("userId");
        Integer status = jsonObject.getInteger("status");
        List<OrderItem> orderItems = new ArrayList<>();
        if(status == -1){//status=-1则查询全部
            orderItems = orderService.findForCustomer(userId);
        }else{//反之，根据status按状态查询
            orderItems = orderService.findForCustomerAndStatus(userId,status);
        }
        return ResponseResult.success(orderItems);
    }

    @PostMapping("/changeItemStatus")
    public ResponseResult changeItemStatus(@RequestBody String data){
        JSONObject jsonObject = JSONObject.parseObject(data);
        Integer orderItemId = jsonObject.getInteger("orderItemId");
        Integer status = jsonObject.getInteger("status");
        orderService.changeItemStatus(orderItemId,status+1);
        return ResponseResult.success();
    }
    @PostMapping("/toDetails")
    public ResponseResult toDetails(@RequestBody String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        Integer orderItemId = jsonObject.getInteger("orderItemId");
        OrderItem orderItem = orderService.findByOrderItemId(orderItemId);
        Orders order = orderService.findById(orderItem.getOrderId());
        //System.out.println("orderItem"+orderItem);
        //System.out.println("order"+order);
        OrderResponse result = new OrderResponse();
        result.setOrder(order);
        result.setOrderItem(orderItem);
        //System.out.println(result);
        return ResponseResult.success(result);
    }
}
