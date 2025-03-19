<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/static/images/list_icon.png" />
		最新博客
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blog" items="${blogList}">
				<li style="margin-bottom: 30px">
					<span class="date"><a href="/blog/${blog.id}.html">
							<fmt:formatDate value="${blog.createDate}" type="date" pattern="yyyy年MM月" />
						</a></span>
					<span class="title"><a href="/blog/${blog.id}.html">${blog.title}</a></span>
					<span class="summary">摘要：${blog.summary}</span>
					<span class="info">
						<fmt:formatDate value="${blog.createDate}" type="date" pattern="yyyy-MM-dd hh:mm" />
						&nbsp; 阅读(${blog.viewCount})
						&nbsp; 评论(${blog.commentCount})
					</span>
				</li>
				<hr style="height:5px;border:none;border-top:1px dashed gray;padding-bottom:  10px;" />
			</c:forEach>

		</ul>
	</div>
</div>

<div>
	<nav>
		<ul class="pagination pagination-sm">
			${pageCode}
		</ul>
	</nav>
</div>