package com.arth.mapper;

import com.arth.pojo.Blog;
import com.arth.pojo.BlogDateCount;
import com.arth.pojo.BlogType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BlogMapper {

    List<Blog> selectAll();

    Integer getTotal();

    //如果定义了typeId,则寻找对应类型BlogType的，如果定义了title则对title列进行模糊查询，如果定义了order则排序 0: 根据主键 1: 根据发表时间 2: 根据点击数 3: 根据评论数
    //如果定义了start和size则从第start条开始获取不超过size条数据
    List<Blog> selectByCondition(Map<String, Object> map);

    Integer getTotalByCondition(Map<String, Object> map);


    Blog selectById(@Param("id") Integer id);

    Integer insert(Blog blog);

    Integer update(Blog blog);

    Integer delete(@Param("id") Integer id);

    List<BlogDateCount> getBlogDateCount();

    Blog getLastBlog(@Param("id") Integer id);

    Blog getNextBlog(@Param("id") Integer id);

    Integer getCurrentId();
}
