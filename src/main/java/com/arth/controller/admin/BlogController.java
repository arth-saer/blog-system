package com.arth.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.arth.pojo.Blog;
import com.arth.pojo.BlogType;
import com.arth.service.BlogService;
import com.arth.service.impl.BlogServiceImpl;
import com.arth.utils.LuceneUtil;
import com.arth.utils.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    private final LuceneUtil luceneUtil = new LuceneUtil();


    @RequestMapping("/list")
    public String getList(@RequestParam(value = "page", required = false) String page,
                          @RequestParam(value = "rows", required = false) String rows,
                          @RequestParam(value = "title", required = false) String title,
                          HttpServletResponse response) throws IOException {

        Map<String, Object> map = new HashMap<>();

        if(page != null && !page.isEmpty() && rows != null && !rows.isEmpty()){
            int page_ = Integer.parseInt(page);
            int rows_ = Integer.parseInt(rows);
            map.put("start", (page_ - 1) * rows_);
            map.put("size", rows_);
        }

        if(title != null && !title.isEmpty()) map.put("title", title);

        List<Blog> blogs = blogService.selectByCondition(map);
        int total = blogService.getTotalByCondition(map);

        com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
        com.alibaba.fastjson.JSONArray jsonArray = JSONArray.parseArray(
                JSON.toJSONString(blogs, SerializerFeature.DisableCircularReferenceDetect)
        );
//        JSONObject result = new JSONObject();
//        JSONArray jsonArray = JSONArray.fromObject(blogs);

        System.out.println(jsonArray);
        result.put("rows", jsonArray);
        result.put("total", total);

        ResponseUtil.write(response, result);
        return null;

    }

    @RequestMapping("/save")
    public String saveBlog(Blog blog, HttpServletResponse response) throws IOException {

        int rows;

        if(blog.getId() != null){
            rows = blogService.update(blog);
            luceneUtil.updateIndex(blog);
        }
        else {
            blog.setCreateDate(new Date());
            blog.setViewCount(0);
            blog.setCommentCount(0);
            rows = blogService.insert(blog);
            blog.setId(rows);
            luceneUtil.insertIndex(blog);
        }

        JSONObject result = new JSONObject();
        if(rows > 0) result.put("success", Boolean.TRUE);
        else result.put("success", Boolean.FALSE);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/getById")
    public String getById(@RequestParam(value = "id", required = false) String id, HttpServletResponse response) throws IOException {
        Blog blog = blogService.selectById(Integer.parseInt(id));
        JSONObject result = JSONObject.fromObject(blog);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/delete")
    public String deleteBlog(@RequestParam("ids") String ids, HttpServletResponse response) throws IOException {

        String[] strings = ids.split(",");
        int rows = 0;
        for (String string : strings) {
            blogService.delete(Integer.parseInt(string));
            luceneUtil.deleteIndex(string);
            rows++;
        }

        net.sf.json.JSONObject result = new net.sf.json.JSONObject();
        if(rows > 0){
            result.put("success", Boolean.TRUE);
        }
        else{
            result.put("success", Boolean.FALSE);
        }
        ResponseUtil.write(response, result);
        return null;

    }

}
