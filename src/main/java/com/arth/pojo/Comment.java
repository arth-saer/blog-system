package com.arth.pojo;

import java.util.Date;
import java.util.SplittableRandom;

public class Comment {
    private Integer id;
    private Integer blogId;
    private String authorIp;
    private String content;
    private Date commentDate;
    private String commentDateStr;
    private Integer status;

    private Blog blog;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getAuthorIp() {
        return authorIp;
    }

    public void setAuthorIp(String authorIp) {
        this.authorIp = authorIp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentDateStr() {
        return commentDateStr;
    }

    public void setCommentDateStr(String commentDateStr) {
        this.commentDateStr = commentDateStr;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

}
