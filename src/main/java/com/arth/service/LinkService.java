package com.arth.service;

import com.arth.pojo.Link;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LinkService {

    List<Link> selectAll();

    List<Link> getSlice(Integer start, Integer size);

    Integer getTotal();

    Link selectById(Integer id);

    Integer update(Link link);

    Integer insert(Link link);

    Integer delete(Integer id);
}
