package com.arth.controller.admin;

import com.arth.pojo.BlogType;
import com.arth.service.BlogService;
import com.arth.service.BlogTypeService;
import com.arth.utils.ResponseUtil;
import net.sf.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeController {

    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/list")
    public String getList(@RequestParam(value = "page", required = false)String page,
                           @RequestParam(value = "rows", required = false) String rows,
                           HttpServletResponse response) throws IOException {

        int page_ = Integer.parseInt(page);
        int rows_ = Integer.parseInt(rows);
        List<BlogType> blogTypes = blogTypeService.getSlice((page_-1) * rows_, rows_);
        int total = blogTypeService.getNumber();
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(blogTypes);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;

    }

    @RequestMapping("/save")
    public String saveBlogType(BlogType blogType, HttpServletResponse response) throws IOException {

        int rows = 0;
        if(blogType.getId() == null){
            rows = blogTypeService.insert(blogType);
        } else{
            rows = blogTypeService.update(blogType);
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

    @RequestMapping("/delete")
    public String deleteBlogType(@RequestParam("ids") String ids, HttpServletResponse response) throws IOException {

        System.out.println(ids);
        net.sf.json.JSONObject result = new net.sf.json.JSONObject();
        String[] strings = ids.split(",");
        int rows = 0, blogCount;
        boolean flag = false;
        Map<String, Object> map = new HashMap<>();

        for (String string : strings) {
            map.put("typeId", Integer.parseInt(string));
            blogCount = blogService.getTotalByCondition(map);
            if (blogCount > 0) {
                result.put("exist", Boolean.TRUE);
                flag = true;
            } else {
                blogTypeService.delete(Integer.parseInt(string));
                rows++;
            }
        }
        if(!flag) result.put("exist", Boolean.FALSE);
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
