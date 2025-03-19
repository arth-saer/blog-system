package com.arth.mapper;

import com.arth.pojo.Blog;
import com.arth.pojo.Blogger;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BloggerMapper {

    Blogger getBlogger();

    Blogger selectByUserName(@Param("userName") String userName);

    Integer update(Blogger blogger);
    Integer updateThePassword(@Param("id") Integer id, @Param("password") String password);
}
