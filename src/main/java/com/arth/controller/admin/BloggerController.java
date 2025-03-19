package com.arth.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.arth.pojo.Blog;
import com.arth.pojo.Blogger;
import com.arth.service.BloggerService;
import com.arth.utils.MD5Util;
import com.arth.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/admin/blogger")
public class BloggerController {

    @Autowired
    BloggerService bloggerService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");

    @RequestMapping("/save")
    public String saveBlogger(Blogger blogger, @RequestParam("imageFile") MultipartFile imageFile,
                              HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(imageFile != null){
            String path1 = request.getServletContext().getRealPath("/")
                    + "static/userImages/";
            String path2 = simpleDateFormat.format(new Date())
                    + "."
                    + imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(path1 + path2));
            blogger.setImageRef(path2);

        }

        request.getSession().setAttribute("currentBlogger", blogger);
        int rows = bloggerService.update(blogger);

        com.alibaba.fastjson.JSONObject result = new JSONObject();
        if(rows > 0){
            result.put("success", Boolean.TRUE);
        }
        else{
            result.put("success", Boolean.FALSE);
        }

        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/saveNewPassword")
    public String saveNewPassword(@RequestParam("id") String id,
                                  @RequestParam("newPassword") String newPassword,
                                  HttpServletResponse response) throws IOException {


        int rows = bloggerService.updateThePassword(Integer.parseInt(id), MD5Util.getMD5(newPassword));


        com.alibaba.fastjson.JSONObject result = new JSONObject();
        if(rows > 0){
            result.put("success", Boolean.TRUE);
        }
        else{
            result.put("success", Boolean.FALSE);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("currentBlogger");
        return "redirect:/login.jsp";
    }


    }
