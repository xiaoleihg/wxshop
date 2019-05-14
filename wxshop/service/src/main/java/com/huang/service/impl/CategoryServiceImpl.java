package com.huang.service.impl;

import com.huang.common.exception.CategoryException;
import com.huang.dao.CategoryDao;
import com.huang.pojo.Category;
import com.huang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Override
    public List<Category> findAll() {
        return categoryDao.selectAll();
    }

    @Override
    public Category findById(String id) {
        return categoryDao.selectById(id);
    }

    @Override
    public void edit(String id, String categoryName) throws CategoryException{
        if(null!= categoryDao.selectByName(categoryName)){
            throw new CategoryException("已存在该分类");
        }
        categoryDao.update(id, categoryName);
    }

    @Override
    public void add(String cid,String categoryName) throws CategoryException{
        if(null!= categoryDao.selectByName(categoryName)){
            throw new CategoryException("已存在该分类");
        }
        categoryDao.insert(cid,categoryName);
    }

    @Override
    public void removeById(String id) {
        categoryDao.deleteById(id);
    }
}
