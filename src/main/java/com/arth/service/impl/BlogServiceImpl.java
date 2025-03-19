package com.arth.service.impl;

import com.arth.mapper.BlogMapper;
import com.arth.mapper.BlogTypeMapper;
import com.arth.mapper.CommentMapper;
import com.arth.pojo.Blog;
import com.arth.pojo.BlogDateCount;
import com.arth.pojo.BlogType;
import com.arth.service.BlogService;
import com.arth.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.smartcardio.ATR;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    BlogTypeMapper blogTypeMapper;



    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public List<Blog> selectAll() {
        List<Blog> blogs = blogMapper.selectAll();
        for (Blog blog : blogs) {
            if(blog != null){
                blog.setBlogType(blogTypeMapper.selectById(blog.getTypeId()));
                blog.setTypeName(blog.getBlogType().getTypeName());
                blog.setCreateDateStr(formatter.format(blog.getCreateDate()));
            }
        }
        return blogs;
    }

    @Override
    @Transactional
    public Integer getTotal() {
        return blogMapper.getTotal();
    }

    @Override
    @Transactional
    public List<Blog> selectByCondition(Map<String, Object> map) {
        List<Blog> blogs = blogMapper.selectByCondition(map);
        for (Blog blog : blogs) {
            if(blog != null){
                blog.setBlogType(blogTypeMapper.selectById(blog.getTypeId()));
                blog.setTypeName(blog.getBlogType().getTypeName());
                blog.setCreateDateStr(formatter.format(blog.getCreateDate()));
            }
        }
        return blogs;
    }

    @Override
    @Transactional
    public Integer getTotalByCondition(Map<String, Object> map) {
        return blogMapper.getTotalByCondition(map);
    }

    @Override
    @Transactional
    public Blog selectById(Integer id) {
        Blog blog = blogMapper.selectById(id);
        if(blog != null){
            blog.setBlogType(blogTypeMapper.selectById(blog.getTypeId()));
            blog.setTypeName(blog.getBlogType().getTypeName());
            blog.setCreateDateStr(formatter.format(blog.getCreateDate()));
        }
        return blog;
    }

    @Override
    @Transactional
    public Integer insert(Blog blog) {
        blogMapper.insert(blog);
        return blogMapper.getCurrentId();
    }

    @Override
    @Transactional
    public Integer update(Blog blog) {

        return blogMapper.update(blog);
    }

    @Override
    @Transactional
    public Integer delete(Integer id) {
        commentMapper.deleteByBlogId(id);
        return blogMapper.delete(id);
    }

    @Override
    @Transactional
    public List<BlogDateCount> getBlogDateCount() {
        return blogMapper.getBlogDateCount();
    }

    @Override
    @Transactional
    public Blog getLastBlog(Integer id) {
        Blog blog = blogMapper.getLastBlog(id);
        if(blog != null){
            blog.setBlogType(blogTypeMapper.selectById(blog.getTypeId()));
            blog.setTypeName(blog.getBlogType().getTypeName());
            blog.setCreateDateStr(formatter.format(blog.getCreateDate()));
        }
        return blog;
    }

    @Override
    @Transactional
    public Blog getNextBlog(Integer id) {
        Blog blog = blogMapper.getNextBlog(id);
        if(blog != null){
            blog.setBlogType(blogTypeMapper.selectById(blog.getTypeId()));
            blog.setTypeName(blog.getBlogType().getTypeName());
            blog.setCreateDateStr(formatter.format(blog.getCreateDate()));
        }
        return blog;
    }
}
