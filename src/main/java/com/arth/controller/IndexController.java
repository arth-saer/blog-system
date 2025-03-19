package com.arth.controller;

import com.arth.pojo.Blog;
import com.arth.service.BlogService;
import com.arth.utils.PageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;


    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "page", required = false) String page,
                              @RequestParam(value = "typeId", required = false) String typeId,
                              @RequestParam(value = "dateStr", required = false) String dateStr,
                              HttpServletRequest request,
                              HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        Map<String, Object> map = new HashMap<>();
        StringBuffer param = new StringBuffer();

        if(page == null || page.isEmpty()) page = "1";
        int page_ = Integer.parseInt(page);
        map.put("start", (page_ - 1) * 10);
        map.put("size", 10);

        if(typeId != null && !typeId.isEmpty()){
            map.put("typeId", Integer.parseInt(typeId));
            param.append("typeId=").append(typeId).append("&");
        }
        if(dateStr != null && !dateStr.isEmpty()){
            map.put("dateStr", dateStr);
            param.append("dateStr=").append(dateStr).append("&");
        }

        List<Blog> blogs = blogService.selectByCondition(map);
        int total = blogService.getTotalByCondition(map);
        String pageCode = PageCodeUtil.getPageCode(request.getContextPath() + "/index.html" , page_, total, param.toString());

        modelAndView.addObject("url", "blog/list.jsp");
        modelAndView.addObject("pageCode", pageCode);
        modelAndView.addObject("blogList", blogs);
        return modelAndView;
    }

}
