package com.arth.service;

import com.arth.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<Comment> selectAll();

    Integer getTotal();

    List<Comment> selectByCondition(Map<String, Object> map);

    Integer getTotalByCondition(Map<String, Object> map);

    Comment selectById(Integer id);

    Integer insert(Comment comment);

    Integer update(Integer id, Integer status);

    Integer delete(Integer id);

    Integer deleteByBlogId(Integer blogId);
}
