package com.huang.dao;

import com.huang.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("categoryDao")
public interface CategoryDao {
    List<Category> selectAll();
    Category selectById(String id);
    Category selectByName(String name);
    void update(@Param("id") String id, @Param("cname") String categoryName);
    void insert(@Param("id")String cid,@Param("cname")String cname);
    void deleteById(String id);
}
