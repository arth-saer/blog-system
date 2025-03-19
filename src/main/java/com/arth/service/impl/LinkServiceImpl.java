package com.arth.service.impl;

import com.arth.mapper.LinkMapper;
import com.arth.pojo.Link;
import com.arth.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    @Transactional
    public List<Link> selectAll() {
        return linkMapper.selectAll();
    }

    @Override
    @Transactional
    public List<Link> getSlice(Integer start, Integer size) {
        return linkMapper.getSlice(start, size);
    }

    @Override
    @Transactional
    public Integer getTotal() {
        return linkMapper.getTotal();
    }

    @Override
    @Transactional
    public Link selectById(Integer id) {
        return linkMapper.selectById(id);
    }

    @Override
    @Transactional
    public Integer update(Link link) {
        return linkMapper.update(link);
    }

    @Override
    @Transactional
    public Integer insert(Link link) {
        return linkMapper.insert(link);
    }

    @Override
    @Transactional
    public Integer delete(Integer id) {
        return linkMapper.delete(id);
    }
}
