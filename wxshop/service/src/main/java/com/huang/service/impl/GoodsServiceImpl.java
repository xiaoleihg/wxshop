package com.huang.service.impl;

import com.huang.common.constant.CommonConstant;
import com.huang.common.utils.FileUtils;
import com.huang.dao.GoodsDao;
import com.huang.params.GoodsParams;
import com.huang.pojo.Business;
import com.huang.pojo.Category;
import com.huang.common.utils.FtpClass;
import com.huang.pojo.Goods;
import com.huang.pojo.OrderItem;
import com.huang.service.GoodsService;
import com.huang.dto.GoodsDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("goodsService")
@ConfigurationProperties(prefix = "ftp")
@PropertySource("classpath:ftp.properties")
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private FtpClass ftpClass;

    @Override
    public void add(GoodsDto goodsDto)throws FileUploadException {
        //System.out.println("goodsDto  in service----"+goodsDto);
        String filePath = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String fileName = FileUtils.renameFileName(goodsDto.getFileName());
        boolean flag = FileUtils.uploadFile(ftpClass, filePath, fileName, goodsDto.getInputStream());
        if (!flag) {
            throw new FileUploadException("文件上传失败");
        }

        // 2.保存到数据库，将DTO转换为POJO
        Goods goods = new Goods();
        try {
            PropertyUtils.copyProperties(goods,goodsDto);
            goods.setGoodsImg(ftpClass.getBaseUrl()+"/"+filePath+"/"+fileName);

            Category category = new Category();
            category.setCid(goodsDto.getCategoryId());
            goods.setCategory(category);

            Business business = new Business();
            business.setId(goodsDto.getBusinessId());
            goods.setBusiness(business);
            //System.out.println("goods----"+goods);
            goodsDao.insert(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkName(String name,String businessId) {
        Goods goods=goodsDao.selectByName(name);
        if(goods!=null && goods.getBusiness().getId()== businessId){
            return false;
        }
        return true;
    }

    @Override
    public List<Goods> findAll() {
        return goodsDao.selectAll();
    }

    @Override
    public List<Goods> findAllForCustomer() {
        return goodsDao.selectAllForCustomer();
    }

    @Override
    public List<Goods> findAllForCustomerByBid(String businessId) {
        return goodsDao.selectAllCustomerByBid(businessId);
    }

    @Override
    public List<Goods> findAllForBusiness(Business business) {
        return goodsDao.selectAllForBusiness(business);
    }

    @Override
    public List<Goods> findByGidList(List<String> gidList) {
        return goodsDao.selectByGidList(gidList);
    }

    @Override
    public Goods findById(String id) {
        return goodsDao.selectById(id);
    }

    @Override
    public void decreaseStock(List<OrderItem> orderItems) {
        goodsDao.updateForBuy(orderItems);
    }

    @Override
    public void edit(GoodsDto goodsDto)throws FileUploadException{
        String filePath = null;
        String fileName = null;
        String headImg = null;
        if(goodsDto.getFileName()!=null && goodsDto.getFileName()!="") {//判断是否有图片传入
            filePath = new SimpleDateFormat("yyyyMMdd").format(new Date());
            fileName = FileUtils.renameFileName(goodsDto.getFileName());
            boolean flag = FileUtils.uploadFile(ftpClass, filePath, fileName, goodsDto.getInputStream());
            if (!flag) {
                throw new FileUploadException("文件上传失败");
            }
            headImg = ftpClass.getBaseUrl()+"/"+filePath+"/"+fileName;
        }

        // 2.保存到数据库，将DTO转换为PO
        Goods goods = new Goods();
        try {
            PropertyUtils.copyProperties(goods,goodsDto);
            goods.setGoodsImg(headImg);

            Category category = new Category();
            category.setCid(goodsDto.getCategoryId());
            goods.setCategory(category);
            goods.setGid(goodsDto.getGid());

            goodsDao.update(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(String id) {
        goodsDao.deleteById(id);
    }

    @Override
    public void getImage(String path, OutputStream outputStream) {

    }

    @Override
    public List<Goods> findByParams(GoodsParams goodsParam) {
        return null;
    }

    @Override
    public List<Goods> findByCategory(String categoryId) {
        return goodsDao.selectByCategory(categoryId);
    }

    @Override
    public void modifyStatus(String id) throws Exception{
        Goods goods = goodsDao.selectById(id);
        int status = goods.getIsValid();
        if(status == 1){
            status = CommonConstant.GOODS_VALID_DOWN;
        }else{
            if(goods.getStock()>0){
                status = CommonConstant.GOODS_VALID_UP;
            }else{
                throw new Exception("商品数量为0，无法上架，请通过修改按钮继续上架");
            }
        }
        goodsDao.updateStatus(id,status);
    }


}
