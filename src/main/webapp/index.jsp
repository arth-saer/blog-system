<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>个人博客系统</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap-theme.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/blog.css">
		<link href="${pageContext.request.contextPath}/favicon.ico" rel="SHORTCUT ICON">
		<script src="${pageContext.request.contextPath}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
		<script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>


		<style type="text/css">
			body {
				padding-top: 10px;
				padding-bottom: 40px;
			}
		</style>
	</head>
	<body>
		<div class="container">
			<jsp:include page="${pageContext.request.contextPath}/foreground/common/head.jsp" />

			<jsp:include page="${pageContext.request.contextPath}/foreground/common/menu.jsp" />

			<div class="row">
				<div class="col-md-9">
					<jsp:include page="${pageContext.request.contextPath}/foreground/${url}"></jsp:include>
				</div>

				<div class="col-md-3">
					<div class="data_list">
						<div class="data_list_title">
							<img src="${pageContext.request.contextPath}/static/images/user_icon.png" />
							博主信息
						</div>
						<div class="user_image">
							<img src="${pageContext.request.contextPath}/static/userImages/${blogger.imageRef}" />
						</div>
						<div class="nickName">${blogger.nickname}</div>
						<div class="userSign">(${blogger.sign})</div>
					</div>

					<div class="data_list">
						<div class="data_list_title">
							<img src="${pageContext.request.contextPath}/static/images/byType_icon.png" />
							按博客类别
						</div>
						<div class="datas">
							<ul>
								<c:forEach var="blogType" items="${blogTypeCountList}">
									<li><span><a href="${pageContext.request.contextPath}/index.html?typeId=${blogType.id}">${blogType.typeName}(${blogType.blogCount})</a></span></li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="data_list">
						<div class="data_list_title">
							<img src="${pageContext.request.contextPath}/static/images/byDate_icon.png" />
							按博客日期
						</div>
						<div class="datas">
							<ul>
								<c:forEach var="blogDateCount" items="${blogDateCountList}">
									<li><span><a href="${pageContext.request.contextPath}/index.html?dateStr=${blogDateCount.dateStr}">${blogDateCount.dateStr}(${blogDateCount.count})</a></span></li>
								</c:forEach>
							</ul>
						</div>
					</div>


					<div class="data_list">
						<div class="data_list_title">
							<img src="${pageContext.request.contextPath}/static/images/link_icon.png" />
							友情链接
						</div>
						<div class="datas">
							<ul>
								<c:forEach var="link" items="${linkList}">
									<li><span><a href="${link.url}">${link.linkName}</a></span></li>
								</c:forEach>
							</ul>
						</div>
					</div>

				</div>


			</div>


		</div>
	</body>
</html>