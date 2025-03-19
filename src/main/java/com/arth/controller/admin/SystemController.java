package com.arth.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.arth.pojo.BlogDateCount;
import com.arth.pojo.BlogType;
import com.arth.pojo.Blogger;
import com.arth.pojo.Link;
import com.arth.service.BlogService;
import com.arth.service.BlogTypeService;
import com.arth.service.BloggerService;
import com.arth.service.LinkService;
import com.arth.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/system")
public class SystemController {

    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private LinkService linkService;

    @RequestMapping("/refresh")
    public String refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext servletContext = request.getServletContext();

        List<BlogType> blogTypes = blogTypeService.selectAll();
        servletContext.setAttribute("blogTypeList", blogTypes);

        List<BlogType> blogTypeCounts = blogTypeService.getCount();
        servletContext.setAttribute("blogTypeCountList", blogTypeCounts);

        List<BlogDateCount> blogDateCounts = blogService.getBlogDateCount();
        servletContext.setAttribute("blogDateCountList", blogDateCounts);

        Blogger blogger = bloggerService.getBlogger();
        blogger.setPassword(null);
        servletContext.setAttribute("blogger", blogger);

        List<Link> links = linkService.selectAll();
        servletContext.setAttribute("linkList", links);

        JSONObject result = new JSONObject();
        result.put("success", Boolean.TRUE);

        ResponseUtil.write(response, result);
        return null;
    }



}
