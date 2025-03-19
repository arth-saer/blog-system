package com.arth.controller;

import com.arth.pojo.Blog;
import com.arth.pojo.Blogger;
import com.arth.service.BloggerService;
import com.arth.utils.MD5Util;
import com.arth.utils.PageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/blogger")
public class FGBloggerController {

    @Autowired
    private BloggerService bloggerService;

    @RequestMapping("/login")
    public String login(Blogger blogger, HttpServletRequest request){
        String userName = blogger.getUserName();
        String password = blogger.getPassword();
        password = MD5Util.getMD5(password);
        Blogger blogger1 = bloggerService.selectByUserName(userName);
        request.getSession().setAttribute("currentBlogger", blogger1);
        if(blogger1 != null && password.equals(blogger1.getPassword())){
            return "redirect:/admin/main.jsp";
        }
        request.setAttribute("blogger", blogger);
        request.setAttribute("errorInfo", "用户名或者密码错误！");
        return "login";
    }

    @RequestMapping("/info")
    public ModelAndView info(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        modelAndView.addObject("url", "/blogger/info.jsp");
        modelAndView.addObject("blogger", bloggerService.getBlogger());

        return modelAndView;
    }


}
