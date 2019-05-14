package com.huang.web.backend.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.common.constant.PageConstant;
import com.huang.common.constant.ResponseStatusConstant;
import com.huang.common.exception.CategoryException;
import com.huang.common.utils.ResponseResult;
import com.huang.pojo.Category;
import com.huang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/backend/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public String list(Integer pageNum,Model model){
        if(ObjectUtils.isEmpty(pageNum) || pageNum==0){
            pageNum= PageConstant.PAGE_NUM;
        }
        //设置分页
        PageHelper.startPage(pageNum,PageConstant.PAGE_SIZE);
        //查找数据
       List<Category> categories = categoryService.findAll();
        //将查找出的结果封装到PageInfo对象中
        PageInfo<Category> pageInfo=new PageInfo<>(categories);
        model.addAttribute("pageInfo",pageInfo);
        return "backend/categoryManager";
    }
    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(String id){
        Category category=categoryService.findById(id);
        return ResponseResult.success(category);
    }
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseResult edit(String id,String name){
        try {
            categoryService.edit(id,name);
            return ResponseResult.success("修改成功");
        } catch (CategoryException e) {
            return ResponseResult.fail(e.getMessage());
        }
    }
    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult add(String name){
        ResponseResult result = new ResponseResult();
        try {
            String cid = "C"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            categoryService.add(cid,name);
            result.setStatus(ResponseStatusConstant.RESPONSE_STATUS_SUCCESS);
            result.setMessage("添加成功");
        } catch (CategoryException e) {
            result.setStatus(ResponseStatusConstant.RESPONSE_STATUS_FAIL);
            result.setMessage(e.getMessage());
        }
        return result;
    }
    @RequestMapping("/remove")
    @ResponseBody
    public ResponseResult removeById(String id){
        categoryService.removeById(id);
        return ResponseResult.success();
    }

}
