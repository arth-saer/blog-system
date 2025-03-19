package com.arth.service;

import com.arth.pojo.Blog;
import com.arth.pojo.BlogDateCount;
import com.arth.pojo.BlogType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BlogService {

    List<Blog> selectAll();

    Integer getTotal();

    List<Blog> selectByCondition(Map<String, Object> map);

    Integer getTotalByCondition(Map<String, Object> map);

    Blog selectById(Integer id);

    Integer insert(Blog blog);

    Integer update(Blog blog);

    Integer delete(Integer id);

    List<BlogDateCount> getBlogDateCount();

    Blog getLastBlog(Integer id);
    Blog getNextBlog(Integer id);
}
