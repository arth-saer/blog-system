package com.arth.controller.admin;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.arth.pojo.Comment;
import com.arth.service.CommentService;
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
@RequestMapping("/admin/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping("/list")
    public String getList(@RequestParam(value = "page", required = false) String page,
                          @RequestParam(value = "rows", required = false) String rows,
                          @RequestParam(value = "status", required = false) String status,
                          HttpServletResponse response) throws IOException {

        Map<String, Object> map = new HashMap<>();

        if(page != null && !page.isEmpty() && rows != null && !rows.isEmpty()){
            int page_ = Integer.parseInt(page);
            int rows_ = Integer.parseInt(rows);
            map.put("start", (page_ - 1) * rows_);
            map.put("size", rows_);
        }

        if(status != null && !status.isEmpty()) map.put("status", Integer.parseInt(status));

        List<Comment> comments = commentService.selectByCondition(map);
        int total = commentService.getTotalByCondition(map);

        JSONObject result = new JSONObject();
        com.alibaba.fastjson.JSONArray jsonArray = JSONArray.parseArray(
                JSON.toJSONString(comments, SerializerFeature.DisableCircularReferenceDetect)
        );


        result.put("rows", jsonArray);
        result.put("total", total);

        ResponseUtil.write(response, result);

        return null;

    }

    @RequestMapping("/delete")
    public String deleteComment(@RequestParam("ids") String ids, HttpServletResponse response) throws IOException {

        String[] strings = ids.split(",");
        int rows = 0;
        for(int i = 0; i < strings.length; i++){
            commentService.delete(Integer.parseInt(strings[i]));
            rows++;
        }

        JSONObject result = new JSONObject();
        if(rows > 0){
            result.put("success", Boolean.TRUE);
        }
        else{
            result.put("success", Boolean.FALSE);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/review")
    public String deleteComment(@RequestParam("ids") String ids,
                                @RequestParam("status") String status,
                                HttpServletResponse response) throws IOException {

        String[] strings = ids.split(",");
        Integer status_ = Integer.parseInt(status);
        int rows = 0;
        for (String string : strings) {
            commentService.update(Integer.parseInt(string), status_);
            rows++;
        }

        JSONObject result = new JSONObject();
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
