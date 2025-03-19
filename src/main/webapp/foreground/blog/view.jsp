<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<script type="text/javascript">
	function showOtherComment() {
		$(".otherComment").show();
	}

	function loadImage(){
		document.getElementById("rangImage").src="${pageContext.request.contextPath}/image.jsp?" + Math.random();
	}

	function submitComment(){
		var content = $("#content").val();
		var imageCode = $("#imageCode").val();
		if(content == null || content == ""){
			alert("请输入评论内容！");
		}else if(imageCode == null || imageCode == ""){
			alert("请输入验证码！");
		}else{
			$.post("${pageContext.request.contextPath}/comment/save.action", {
				"content":content,
				"imageCode":imageCode,
				"blogId":${blog.id}
			}, function(result){
				if(result.success){
					window.location.reload();
					alert("评论提交成功，审核通过后显示！");
				}else{
					alert(result.errorInfo);
				}
			}, "json");
		}
	}
	function query1(keyword){
		$("#q1").val(keyword);
		$("#searchForm").submit();
	}

</script>

<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/static/images/blog_show_icon.png" />
		博客详细信息
	</div>
	<div>
		<div class="blog_title">
			<h3><strong>${blog.title}</strong></h3>
		</div>
		<br />

		<div class="blog_info">
			发布时间：【
			<fmt:formatDate value="${blog.createDate}" type="date" pattern="yyyy-MM-dd hh:mm" />】
			&nbsp; &nbsp; 类型：${blog.blogType.typeName}
			&nbsp; &nbsp; 阅读：${blog.viewCount}
			&nbsp; &nbsp; 评论：${blog.commentCount}
		</div>

		<br />

		<div class="blog_content">
			${blog.content}
		</div>

		<div class="blog_keyWord">
			<font><strong>关键字：</strong></font>
			<c:choose>
				<c:when test="${keywords == null}">
					&nbsp;&nbsp;无
				</c:when>
				<c:otherwise>
					<form id = "searchForm" action="${pageContext.request.contextPath}/blog/search.html" method="post"  target="_blank">
						<input type="hidden" id="q1" name="q"/>
						<c:forEach var="keyword" items="${keywords}">
							&nbsp;&nbsp;<a href="javascript:query1('${keyword}')">${keyword}</a>&nbsp;&nbsp;
						</c:forEach>
					</form>
				</c:otherwise>
			</c:choose>
		</div>

		<div>
			${pageCode}
		</div>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/static/images/comment_icon.png" />
		评论信息
		<c:if test="${commentList.size() > 10}">
			<a href="javascript:showOtherComment()" style="float:right; padding-right: 40px;">显示所有评论</a>
		</c:if>
	</div>
	<div class="commentDatas">
		<c:choose>
			<c:when test="${commentList.size() == 0}">
				暂无评论
			</c:when>
			<c:otherwise>
				<c:forEach var="comment" items="${commentList}" varStatus="status">
					<c:choose>
						<c:when test="${status.index < 10}">
							<div class="comment">
								<span>
									<font>
										${status.index + 1}楼 &nbsp;&nbsp;
										${comment.authorIp}： &nbsp;&nbsp;&nbsp;
									</font>
									${comment.content} &nbsp;&nbsp;&nbsp;&nbsp;
									【
									<fmt:formatDate value="${comment.commentDate}" type="date"
										pattern="yyyy-MM-dd hh:mm:ss" />】
								</span>
							</div>
						</c:when>
						<c:otherwise>
							<div class="otherComment">
								<div class="comment">
								<span>
									<font>
										${status.index + 1}楼 &nbsp;&nbsp;
										${comment.authorIp}： &nbsp;&nbsp;&nbsp;
									</font>
									${comment.content} &nbsp;&nbsp;&nbsp;&nbsp;
									【
									<fmt:formatDate value="${comment.commentDate}" type="date"
										pattern="yyyy-MM-dd hh:mm:ss" />】
								</span>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/static/images/publish_comment_icon.png" />
		发表评论
	</div>

	<div class="publish_comment">
		<div>
			<textarea id="content" name="content" rows="3" style="width: 100%;" placeholder="来说说你的看法吧..."></textarea>
		</div>

		<div class="verCode">
			验证码:<input id="imageCode" name="imageCode" size="10" onkeydown="if(event.keyCode == 13) submitComment()"/>
			&nbsp;<img src="${pageContext.request.contextPath}/image.jsp" id="rangImage" name="rangImage" title="换一张试试"
			onclick="javascript:loadImage()" width="60" height="20" border="1" align="absmiddle"/>
		</div>

		<div class="publishButton">
			<button class="btn btn-primary" type="button" onclick="submitComment()">发表评论</button>
		</div>
	</div>

</div>