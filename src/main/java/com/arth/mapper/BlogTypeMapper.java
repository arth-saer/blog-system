package com.arth.mapper;

import com.arth.pojo.BlogType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogTypeMapper {
    List<BlogType> selectAll();

    List<BlogType> getSlice(@Param("start") Integer start, @Param("size") Integer size);
    BlogType selectById(@Param("id") Integer id);

    Integer getTotal();

    Integer update(BlogType blogType);

    Integer insert(BlogType blogType);

    Integer delete(@Param("id") Integer id);

    List<BlogType> getCount();

}
