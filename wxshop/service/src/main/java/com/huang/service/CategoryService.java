package com.huang.service;

import com.huang.common.exception.CategoryException;
import com.huang.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CategoryService {
    List<Category> findAll();
    Category findById(String id);
    void edit(@Param("id") String id, @Param("categoryName") String categoryName) throws CategoryException;
    void add(String cid,String categoryName) throws CategoryException;
    void removeById(String id);
}
