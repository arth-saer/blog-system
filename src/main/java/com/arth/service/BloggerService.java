package com.arth.service;

import com.arth.pojo.Blogger;
import org.apache.ibatis.annotations.Param;

public interface BloggerService {
    Blogger selectByUserName(String userName);

    Integer update(Blogger blogger);

    Integer updateThePassword(Integer id, String password);

    Blogger getBlogger();

}
