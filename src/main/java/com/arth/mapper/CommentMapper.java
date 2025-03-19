package com.arth.mapper;

import com.arth.pojo.Blog;
import com.arth.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentMapper {

    List<Comment> selectAll();

    Integer getTotal();

    List<Comment> selectByCondition(Map<String, Object> map);

    Integer getTotalByCondition(Map<String, Object> map);

    Comment selectById(@Param("id") Integer id);

    Integer insert(Comment comment);

    Integer update(@Param("id") Integer id, @Param("status") Integer status);

    Integer delete(@Param("id") Integer id);

    Integer deleteByBlogId(@Param("blogId") Integer blogId);
}
