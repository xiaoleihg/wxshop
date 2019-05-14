package com.huang.service;

import com.huang.params.GoodsParams;
import com.huang.pojo.Business;
import com.huang.pojo.Goods;
import com.huang.dto.GoodsDto;
import com.huang.pojo.OrderItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;

import java.io.OutputStream;
import java.util.List;

public interface GoodsService {
    void add(GoodsDto goodsDto)throws FileUploadException;
    boolean checkName(String name,String businessId);//判断商家时由已经添加了该商品
    List<Goods> findAll();
    List<Goods> findAllForCustomer();
    List<Goods> findAllForCustomerByBid(String businessId);
    List<Goods> findAllForBusiness(Business business);
    List<Goods> findByGidList(List<String> gidList);
    Goods findById(String id);
    void decreaseStock(List<OrderItem> orderItems);//用户购买，减少库存
    void edit(GoodsDto goodsDto)throws FileUploadException;
    void removeById(String id);
    void getImage(String path, OutputStream outputStream);
    List<Goods> findByParams(GoodsParams goodsParam);
    List<Goods> findByCategory(String categoryId);

    void modifyStatus(String id) throws Exception;
}
