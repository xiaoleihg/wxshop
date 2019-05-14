package com.huang.dao;

import com.huang.params.GoodsParams;
import com.huang.pojo.Business;
import com.huang.pojo.Goods;

import com.huang.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("goodsDao")
public interface GoodsDao {
    List<Goods> selectAll();
    List<Goods> selectAllForCustomer();//查找上架状态的商品,并返回给客户
    List<Goods> selectAllForBusiness(Business business);//查找店家的所有商品，并返回给店家
    List<Goods> selectAllCustomerByBid(String businessId);//查找店家的所有商品，并返回给店家
    List<Goods> selectByGidList(@Param("gidList") List<String> gidList);//查找购物车中的商品
    Goods selectById(String id);
    Goods selectByName(String name);
    void update(Goods goods);
    void updateForBuy(@Param("orderItems") List<OrderItem> orderItems);
    void insert(Goods goods);
    void deleteById(String id);
    List<Goods> selectByParam(GoodsParams goodsParams);
    List<Goods> selectByCategory(String categoryId);

    void updateStatus(@Param("id") String id, @Param("status")int status);
}
