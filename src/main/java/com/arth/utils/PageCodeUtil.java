package com.arth.utils;

public class PageCodeUtil {

    public static String getPageCode(String targetUrl, int currentPage, int total, String param){
        StringBuffer buffer = new StringBuffer();
        if(total == 0) return "未查询到数据！";
        int totalPage;
        if(total % 10 == 0) totalPage = total / 10;
        else totalPage = total/10 + 1;

        buffer.append("<li><a href='").append(targetUrl).append("?page=1&").
                append(param).append("'>首页</a></li>");

        if(currentPage > 1){
            buffer.append("<li><a href='").append(targetUrl).append("?page=").
                    append(currentPage - 1).append("&").append(param).append("'>上一页</a></li>");
        }else{
            buffer.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }

        for(int i = 1; i <= totalPage; i++){
            buffer.append("<li><a href='").append(targetUrl).append("?page=").
                    append(i).append("&").append(param).append("'>").append(i).append("</a></li>");
        }

        if(currentPage < totalPage){
            buffer.append("<li><a href='").append(targetUrl).append("?page=").
                    append(currentPage + 1).append("&").append(param).append("'>下一页</a></li>");
        }else{
            buffer.append("<li class='disabled'><a href='#'>下一页</a></li>");
        }

        buffer.append("<li><a href='").append(targetUrl).append("?page=").
                append(totalPage).append("&").append(param).append("'>尾页</a></li>");


        return buffer.toString();
    }

}
