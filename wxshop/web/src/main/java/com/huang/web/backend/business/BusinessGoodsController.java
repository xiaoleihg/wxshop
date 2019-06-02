package com.huang.web.backend.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huang.common.constant.CommonConstant;
import com.huang.common.constant.PageConstant;
import com.huang.common.utils.ResponseResult;
import com.huang.pojo.Business;
import com.huang.pojo.Category;
import com.huang.pojo.Goods;
import com.huang.service.CategoryService;
import com.huang.service.GoodsService;
import com.huang.dto.GoodsDto;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Controller
@RequestMapping("/backend/business/goods")
public class BusinessGoodsController {
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
    public String list(Integer pageNum,Model model,HttpSession session){
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum = PageConstant.PAGE_NUM;
        }
        Business business = (Business) session.getAttribute("business");
        PageHelper.startPage(pageNum,PageConstant.PAGE_SIZE);
        List<Goods> goodsList = goodsService.findAllForBusiness(business);
        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        model.addAttribute("pageInfo",pageInfo);
        return "backend/businessGoodsManager";
    }

    @RequestMapping("/add")
    public String add(GoodsVo goodsVo, Integer pageNum, HttpSession session, Model model) {
        Business business = (Business) session.getAttribute("business");
        if(goodsVo.getGoodsName()==null){
            model.addAttribute("errorMsg", "添加失败，请输入商品名");
            return "forward:list?pageNum=" + pageNum;
        }else if(!goodsService.checkName(goodsVo.getGoodsName(),business.getId())){
            model.addAttribute("errorMsg", "店中已有该商品，您只要修改库存就可以了 ");
            return "forward:list?pageNum=" + pageNum;
        }
        goodsVo.setGid("G"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        goodsVo.setIsValid(1);//设置上架状态。默认上架
        goodsVo.setBusinessId(business.getId());
        //System.out.println("add--vo----"+goodsVo);
        try {
            //将VO转换为DTO
            GoodsDto goodsDto = new GoodsDto();
            PropertyUtils.copyProperties(goodsDto, goodsVo);  //对象间属性的拷贝
            goodsDto.setFileName(goodsVo.getFile().getOriginalFilename());
            goodsDto.setInputStream(goodsVo.getFile().getInputStream());
            goodsService.add(goodsDto);
            model.addAttribute("successMsg", "添加成功");
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }
        return "forward:list?pageNum=" + pageNum;
    }

    @RequestMapping("/remove")//删除
    @ResponseBody
    public ResponseResult removeById(String id) {
        goodsService.removeById(id);
        return ResponseResult.success();
    }
    @RequestMapping("/findById")//查看商品详情
    @ResponseBody
    public ResponseResult findById(String id) {
        Goods goods = goodsService.findById(id);
        return ResponseResult.success(goods);
    }
    @RequestMapping("/modifyStatus")//修改商品状态，上下架处理
    @ResponseBody
    public ResponseResult modifyStatus(String id,Model model){
        try {
            goodsService.modifyStatus(id);
            return ResponseResult.success();
        }catch (Exception e){
            model.addAttribute("errorMsg", e.getMessage());
            return ResponseResult.fail();
        }
    }
    @RequestMapping("/edit")
    public String edit(GoodsVo goodsVo, Integer pageNum, HttpSession session, Model model) {
        if(goodsVo.getGoodsName()==null){
            model.addAttribute("errorMsg", "修改失败，商品名为空");
            return "forward:list?pageNum=" + pageNum;
        }
        //System.out.println("edit--vo----"+goodsVo);
        if(goodsVo.getStock()<1 && goodsVo.getIsValid()==CommonConstant.GOODS_VALID_UP){//库存小于1的商品不能上架
            model.addAttribute("errorMsg", "修改失败,库存小于1的商品不能上架");
            return "forward:list?pageNum=" + pageNum;
        }
        try {
            //将VO转换为DTO
            GoodsDto goodsDto = new GoodsDto();
            PropertyUtils.copyProperties(goodsDto, goodsVo);  //对象间属性的拷贝
            goodsDto.setInputStream(goodsVo.getFile().getInputStream());
            goodsDto.setFileName(goodsVo.getFile().getOriginalFilename());
            goodsService.edit(goodsDto);
            model.addAttribute("successMsg", "修改成功");
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        return "forward:list?pageNum=" + pageNum;
    }

}
