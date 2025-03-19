package com.arth.controller;

import com.arth.pojo.Blog;
import com.arth.service.BlogService;
import com.arth.service.CommentService;
import com.arth.utils.LuceneUtil;
import com.arth.utils.PageCodeUtil;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/blog")
public class FGBlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    private LuceneUtil luceneUtil = new LuceneUtil();

    @RequestMapping("/{id}")
    public ModelAndView showBlog(@PathVariable("id") Integer id, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        Blog blog = blogService.selectById(id);
        blog.setViewCount(blog.getViewCount() + 1);
        blogService.update(blog);

        Map<String, Object> map = new HashMap<>();
        map.put("blogId", id);
        map.put("status", 1);

        modelAndView.addObject("commentList", commentService.selectByCondition(map));
        modelAndView.addObject("pageCode", getPageCode(blogService.getLastBlog(id), blogService.getNextBlog(id), request.getContextPath()));
        modelAndView.addObject("url", "blog/view.jsp");
        modelAndView.addObject("blog", blog);
        String keyword = blog.getKeyword();
        if(keyword == null || keyword.isEmpty()) modelAndView.addObject("keywords", null);
        else{
            String[] keywords = keyword.split(" ");
            List<String> strings = new LinkedList<>();
            for (String s : keywords) {
                if(s != null && !s.isEmpty()) strings.add(s);
            }
            modelAndView.addObject("keywords", strings);
        }

        return modelAndView;

    }
    private String getPageCode(Blog lastBlog, Blog nextBlog, String path){
        StringBuffer buffer = new StringBuffer();

        if(lastBlog == null || lastBlog.getId() == null){
            buffer.append("<p>上一篇：没有啦！</p>");
        }else {
            buffer.append("<p>上一篇：<a href='").append(path).append("/blog/").append(lastBlog.getId()).
                    append(".html").append("'>").append(lastBlog.getTitle()).append("</a><p>");
        }

        if(nextBlog == null || nextBlog.getId() == null){
            buffer.append("<p>下一篇：没有啦！</p>");
        }else {
            buffer.append("<p>下一篇：<a href='").append(path).append("/blog/").append(nextBlog.getId()).
                    append(".html").append("'>").append(nextBlog.getTitle()).append("</a><p>");
        }
        return buffer.toString();
    }



    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(value = "q", required = false) String q,
                               @RequestParam(value = "page", required = false) String page,
                               HttpServletRequest request
                               ) throws InvalidTokenOffsetsException, IOException, ParseException {

        if(page == null || page.isEmpty()) page = "1";

        List<Blog> blogs = luceneUtil.search(q.trim());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        modelAndView.addObject("url", "/blog/result.jsp");
        modelAndView.addObject("q", q.trim());
        modelAndView.addObject("total", blogs.size());
        modelAndView.addObject("blogList", blogs.subList(Integer.parseInt(page) * 10 - 10, Math.min(Integer.parseInt(page) * 10, blogs.size())));

        modelAndView.addObject("pageCode", PageCodeUtil.getPageCode(request.getContextPath() + "/blog/search.html", Integer.parseInt(page), blogs.size(), "q="+q));
        return modelAndView;

    }


}
