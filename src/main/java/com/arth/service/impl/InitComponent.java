package com.arth.service.impl;

import com.arth.pojo.*;
import com.arth.service.BlogService;
import com.arth.service.BlogTypeService;
import com.arth.service.BloggerService;
import com.arth.service.LinkService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

@Component
public class InitComponent implements ServletContextListener, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    @Transactional
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        BlogTypeService blogTypeService = applicationContext.getBean(BlogTypeService.class);
        List<BlogType> blogTypes = blogTypeService.selectAll();
        servletContext.setAttribute("blogTypeList", blogTypes);

        List<BlogType> blogTypeCounts = blogTypeService.getCount();
        servletContext.setAttribute("blogTypeCountList", blogTypeCounts);

        BlogService blogService = applicationContext.getBean(BlogService.class);
        List<BlogDateCount> blogDateCounts = blogService.getBlogDateCount();
        servletContext.setAttribute("blogDateCountList", blogDateCounts);

        BloggerService bloggerService = applicationContext.getBean(BloggerService.class);
        Blogger blogger = bloggerService.getBlogger();
        blogger.setPassword(null);
        servletContext.setAttribute("blogger", blogger);

        LinkService linkService = applicationContext.getBean(LinkService.class);
        List<Link> links = linkService.selectAll();
        servletContext.setAttribute("linkList", links);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       InitComponent.applicationContext = applicationContext;
    }
}
