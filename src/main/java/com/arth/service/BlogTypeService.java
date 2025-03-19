package com.arth.service;

import com.arth.pojo.BlogType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogTypeService {

    List<BlogType> selectAll();

    List<BlogType> getSlice(Integer start, Integer size);

    BlogType selectById(Integer id);

    Integer getNumber();

    Integer update(BlogType blogType);

    Integer insert(BlogType blogType);

    Integer delete(Integer id);

    List<BlogType> getCount();
}
