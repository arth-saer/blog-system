package com.arth;

import com.alibaba.druid.pool.DruidDataSource;
import com.arth.controller.FGBlogController;
import com.arth.pojo.Blog;
import com.arth.service.BlogService;
import com.arth.service.impl.BlogServiceImpl;
import com.arth.utils.MD5Util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.ContextExposingHttpServletRequest;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        BlogService blogService = new BlogServiceImpl();
        Integer id =10;
        Blog last = blogService.getLastBlog(id);
        Blog next = blogService.getNextBlog(id);
        System.out.println(last);
        System.out.println(next);
    }
}