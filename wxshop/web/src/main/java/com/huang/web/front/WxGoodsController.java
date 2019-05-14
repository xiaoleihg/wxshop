package com.huang.web.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.huang.common.utils.ResponseResult;
import com.huang.pojo.Business;
import com.huang.pojo.Goods;
import com.huang.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController("wxGoodsController")
@RequestMapping("/wxGoods")
public class WxGoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/findAll")
    public ResponseResult findAll(){
        List<Goods> goodsList = goodsService.findAllForCustomer();
        //System.out.println(goodsList);
        return ResponseResult.success(goodsList);
    }

    @GetMapping("/findById")
    public ResponseResult findById(String id){
        Goods goods = goodsService.findById(id);
       // System.out.println(goods);
        return ResponseResult.success(goods);
    }

    @PostMapping("/findFromCart")
    public ResponseResult findFromCart(@RequestBody String data){
        JSONArray jsonArray = JSON.parseArray(data);
        List<String> gidList = jsonArray.toJavaList(String.class);
        List<Goods> fromCart = goodsService.findByGidList(gidList);
        return ResponseResult.success(fromCart);
    }

    @GetMapping("/findByCategory")
    public ResponseResult findByCategory(String categoryId){
        List<Goods> goodsListByCategory = goodsService.findByCategory(categoryId);
        return ResponseResult.success(goodsListByCategory);
    }

    @GetMapping("/findByShop")
    public ResponseResult findByShop(String businessId) {
        System.out.println(businessId);
        List<Goods> allForCustomerByBid = goodsService.findAllForCustomerByBid(businessId);
        return ResponseResult.success(allForCustomerByBid);
    }
}
