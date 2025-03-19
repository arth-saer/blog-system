package com.arth.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.arth.pojo.BlogType;
import com.arth.pojo.Link;
import com.arth.service.BlogService;
import com.arth.service.BlogTypeService;
import com.arth.service.LinkService;
import com.arth.utils.ResponseUtil;
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
@RequestMapping("/admin/link")
public class LinkController {
    @Autowired
    private LinkService linkService;


    @RequestMapping("/list")
    public String getList(@RequestParam(value = "page", required = false)String page,
                          @RequestParam(value = "rows", required = false) String rows,
                          HttpServletResponse response) throws IOException {

        int page_ = Integer.parseInt(page);
        int rows_ = Integer.parseInt(rows);
        List<Link> links = linkService.getSlice((page_-1) * rows_, rows_);
        int total = linkService.getTotal();

        com.alibaba.fastjson.JSONObject result = new JSONObject();
        com.alibaba.fastjson.JSONArray jsonArray = JSONArray.parseArray(
                JSON.toJSONString(links, SerializerFeature.DisableCircularReferenceDetect)
        );

        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;

    }

    @RequestMapping("/save")
    public String saveLink(Link link, HttpServletResponse response) throws IOException {

        int rows = 0;
        if(link.getId() == null){
            rows = linkService.insert(link);
        } else{
            rows = linkService.update(link);
        }

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

    @RequestMapping("/delete")
    public String deleteLink(@RequestParam("ids") String ids, HttpServletResponse response) throws IOException {

        String[] strings = ids.split(",");
        int rows = 0;
        for (String string : strings) {
            linkService.delete(Integer.parseInt(string));
            rows++;
        }

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
}
