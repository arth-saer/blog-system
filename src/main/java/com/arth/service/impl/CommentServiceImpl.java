package com.arth.service.impl;

import com.arth.mapper.BlogMapper;
import com.arth.mapper.CommentMapper;
import com.arth.pojo.Comment;
import com.arth.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    BlogMapper blogMapper;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public List<Comment> selectAll() {
        List<Comment> comments = commentMapper.selectAll();
        for (Comment comment : comments) {
            comment.setBlog(blogMapper.selectById(comment.getBlogId()));
            comment.setCommentDateStr(formatter.format(comment.getCommentDate()));
        }
        return comments;
    }

    @Override
    @Transactional
    public Integer getTotal() {
        return commentMapper.getTotal();
    }

    @Override
    @Transactional
    public List<Comment> selectByCondition(Map<String, Object> map) {
        List<Comment> comments = commentMapper.selectByCondition(map);
        for (Comment comment : comments) {
            comment.setBlog(blogMapper.selectById(comment.getBlogId()));
            comment.setCommentDateStr(formatter.format(comment.getCommentDate()));
        }
        return comments;
    }

    @Override
    @Transactional
    public Integer getTotalByCondition(Map<String, Object> map) {
        return commentMapper.getTotalByCondition(map);
    }

    @Override
    @Transactional
    public Comment selectById(Integer id) {
        Comment comment = commentMapper.selectById(id);
        comment.setBlog(blogMapper.selectById(comment.getBlogId()));
        comment.setCommentDateStr(formatter.format(comment.getCommentDate()));
        return comment;
    }

    @Override
    @Transactional
    public Integer insert(Comment comment) {
        return commentMapper.insert(comment);
    }

    @Override
    @Transactional
    public Integer update(Integer id, Integer status) {
        return commentMapper.update(id, status);
    }

    @Override
    @Transactional
    public Integer delete(Integer id) {
        return commentMapper.delete(id);
    }

    @Override
    @Transactional
    public Integer deleteByBlogId(Integer blogId) {
        return commentMapper.deleteByBlogId(blogId);
    }
}
