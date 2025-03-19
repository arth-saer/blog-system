package com.arth.service.impl;

import com.arth.mapper.BloggerMapper;
import com.arth.pojo.Blogger;
import com.arth.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BloggerServiceImpl implements BloggerService {

    @Autowired
    private BloggerMapper bloggerMapper;

    @Override
    @Transactional
    public Blogger selectByUserName(String userName) {
        return bloggerMapper.selectByUserName(userName);
    }

    @Override
    @Transactional
    public Integer update(Blogger blogger) {
        return bloggerMapper.update(blogger);
    }

    @Override
    public Integer updateThePassword(Integer id, String password) {
        return bloggerMapper.updateThePassword(id, password);
    }

    @Override
    public Blogger getBlogger() {
        return bloggerMapper.getBlogger();
    }


}
