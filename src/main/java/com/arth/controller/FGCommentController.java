package com.arth.controller;

import com.alibaba.fastjson.JSONObject;
import com.arth.pojo.Blog;
import com.arth.pojo.Comment;
import com.arth.service.BlogService;
import com.arth.service.CommentService;
import com.arth.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/comment")
public class FGCommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/save")
    public String insertComment(Comment comment, @RequestParam("imageCode") String imageCode,
                                HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String sRand = (String) session.getAttribute("sRand");
        JSONObject result = new JSONObject();
        if(!imageCode.equals(sRand)){
            result.put("success", Boolean.FALSE);
            result.put("errorInfo", "验证码错误！");
        }else{
            comment.setAuthorIp(request.getRemoteAddr());
            int rows = commentService.insert(comment);
            Blog blog = blogService.selectById(comment.getBlogId());
            blog.setCommentCount(blog.getCommentCount() + 1);
            blogService.update(blog);

            if(rows > 0) result.put("success", Boolean.TRUE);
            else result.put("success", Boolean.FALSE);
        }
        ResponseUtil.write(response, result);
        return null;
    }

}
