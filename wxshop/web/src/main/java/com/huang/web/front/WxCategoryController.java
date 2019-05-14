package com.huang.web.front;

import com.huang.common.utils.ResponseResult;
import com.huang.pojo.Category;
import com.huang.pojo.Goods;
import com.huang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("wxCategoryController")
@RequestMapping("/wxCategory")
public class WxCategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/list")
    public ResponseResult findAll(){
        List<Category> categories = categoryService.findAll();
        return ResponseResult.success(categories);
    }
}
