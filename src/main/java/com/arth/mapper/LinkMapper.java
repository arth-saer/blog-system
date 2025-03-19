package com.arth.mapper;

import com.arth.pojo.BlogType;
import com.arth.pojo.Link;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkMapper {
    List<Link> selectAll();

    List<Link> getSlice(@Param("start") Integer start, @Param("size") Integer size);

    Integer getTotal();

    Link selectById(@Param("id") Integer id);

    Integer update(Link link);

    Integer insert(Link link);

    Integer delete(@Param("id") Integer id);
}
