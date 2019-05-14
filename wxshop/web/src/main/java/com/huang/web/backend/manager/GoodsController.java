package com.huang.web.backend.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.common.constant.CommonConstant;
import com.huang.common.constant.PageConstant;
import com.huang.common.utils.ResponseResult;
import com.huang.dto.GoodsDto;
import com.huang.pojo.Category;
import com.huang.pojo.Goods;
import com.huang.service.CategoryService;
import com.huang.service.GoodsService;
import com.huang.vo.GoodsVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/backend/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CategoryService categoryService;

    //@ModelAttribute 用法之一：在方法的上面（不在参数前面）
    //作用：在调用所有目标方法前都会调用添加@ModelAttribute注解的方法，并向模型中添加数据
    @ModelAttribute("categories")
    public List<Category> loadProductTypes() {
        List<Category> categories = categoryService.findAll();
        return categories;
    }

    @RequestMapping("/list")
    public String list(Integer pageNum,Model model){
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = PageConstant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,PageConstant.PAGE_SIZE);
        List<Goods> goodsList = goodsService.findAll();
        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        model.addAttribute("pageInfo",pageInfo);
        return "backend/goodsManager";
    }

    @RequestMapping("/remove")
    @ResponseBody
    public ResponseResult removeById(String id) {
        goodsService.removeById(id);
        return ResponseResult.success();
    }

    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(String id) {
        Goods goods = goodsService.findById(id);
        return ResponseResult.success(goods);
    }

}
