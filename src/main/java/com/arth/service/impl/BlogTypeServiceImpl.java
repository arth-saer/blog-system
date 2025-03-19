package com.arth.service.impl;

import com.arth.mapper.BlogTypeMapper;
import com.arth.pojo.BlogType;
import com.arth.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogTypeServiceImpl implements BlogTypeService {

    @Autowired
    BlogTypeMapper blogTypeMapper;

    @Override
    @Transactional
    public List<BlogType> selectAll() {
        return blogTypeMapper.selectAll();
    }

    @Override
    @Transactional
    public List<BlogType> getSlice(Integer start, Integer size) {
        return blogTypeMapper.getSlice(start, size);
    }

    @Override
    @Transactional
    public BlogType selectById(Integer id) {
        return blogTypeMapper.selectById(id);
    }

    @Override
    @Transactional
    public Integer getNumber() {
        return blogTypeMapper.getTotal();
    }

    @Override
    @Transactional
    public Integer update(BlogType blogType) {
        return blogTypeMapper.update(blogType);
    }

    @Override
    @Transactional
    public Integer insert(BlogType blogType) {
        return blogTypeMapper.insert(blogType);
    }

    @Override
    @Transactional
    public Integer delete(Integer id) {
        return blogTypeMapper.delete(id);
    }

    @Override
    public List<BlogType> getCount() {
        return blogTypeMapper.getCount();
    }
}
