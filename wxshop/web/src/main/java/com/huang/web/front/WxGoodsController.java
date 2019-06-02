package com.huang.web.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.huang.common.utils.ResponseResult;
import com.huang.params.GoodsParams;
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

    @PostMapping("/findAll")//查询所有商品
    public ResponseResult findAll(){
        List<Goods> goodsList = goodsService.findAllForCustomer();
        return ResponseResult.success(goodsList);
    }
    @GetMapping("/findById")//根据商品编号查找，用于查看商品详情
    public ResponseResult findById(String id){
        Goods goods = goodsService.findById(id);
        return ResponseResult.success(goods);
    }
    @GetMapping("/findByCategory")//分类查询商品
    public ResponseResult findByCategory(String categoryId){
        List<Goods> goodsListByCategory = goodsService.findByCategory(categoryId);
        return ResponseResult.success(goodsListByCategory);
    }
    @GetMapping("/findByShop")//点击“进店看看”浏览商店
    public ResponseResult findByShop(String businessId) {
        List<Goods> allForCustomerByBid = goodsService.findAllForCustomerByBid(businessId);
        return ResponseResult.success(allForCustomerByBid);
    }
    @PostMapping("/findByName")//用户搜索商品
    public ResponseResult findByName(@RequestBody GoodsParams params){
        List<Goods> goodsListByName = goodsService.findByParams(params);
        if (goodsListByName==null){
            return ResponseResult.fail("抱歉，没找到你要的商品");
        }
        return ResponseResult.success(goodsListByName);
    }
    @PostMapping("/findFromCart")//返回购物车中商品的信息
    public ResponseResult findFromCart(@RequestBody String data){
        JSONArray jsonArray = JSON.parseArray(data);
        List<String> gidList = jsonArray.toJavaList(String.class);
        List<Goods> fromCart = goodsService.findByGidList(gidList);
        return ResponseResult.success(fromCart);
    }

}

