<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>博客管理页面</title>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js">
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js">
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js">
		</script>

		<script type="text/javascript">
			var ue = UE.getEditor("editor");
			UE.getEditor("editor").addListener("ready", function() {
				UE.getEditor("editor").setContent($("#profile").val());
			});

			function submitInfo() {
				var nickname = $("#nickname").val();
				var sign = $("#sign").val();
				var profile = UE.getEditor("editor").getContent();

				if (nickname == null || nickname == '') {
					$.messager.alert("系统提示", "请输入昵称");
				} else {
					$("#profile").val(profile);
					$("#fm").form("submit", {
						onSubmit: function() {
							return true;
						},
						success: function(result) {
							var result = eval('(' + result + ')');
							if (result.success) {
								$.messager.alert("系统提示", "保存成功！");
							} else {
								$.messager.alert("系统提示", "保存失败！");
							}
						}
					});
				}
			}
		</script>

	</head>

	<body style="margin: 10px">

		<div id="p" class="easyui-panel" title="修改个人信息" style="padding: 10px">
			<form id="fm" action="${pageContext.request.contextPath}/admin/blogger/save.action" method="post"
				enctype="multipart/form-data">
				<input type="hidden" id="id" name="id" value="${currentBlogger.id}" />
				<input type="hidden" id="profile" name="profile" value="${currentBlogger.profile}" />
				<table cellspacing="20px">
					<tr>
						<td width="80px">用户名:</td>
						<td><input type="text" id="userName" name="userName" style="width: 200px"
								value="${currentBlogger.userName}" readonly="readonly" /></td>
					</tr>
					<tr>
						<td width="80px">昵称:</td>
						<td><input type="text" id="nickname" name="nickname" style="width: 200px"
								value="${currentBlogger.nickname}" /></td>
					</tr>
					<tr>
						<td width="80px">个性签名:</td>
						<td><input type="text" id="sign" name="sign" style="width: 400px"
								value="${currentBlogger.sign}" /></td>
					</tr>
					<tr>
						<td width="80px">个人头像:</td>
						<td><input type="file" id="imageFile" name="imageFile" style="width: 400px" /></td>
					</tr>

					<tr>
						<td width="80px">个人简介：</td>
						<td>
							<script id="editor" type="text/plain" style="width: 100%; height: 400px;"></script>
						</td>
					</tr>
					<tr>
						<td></td>
						<td><a href="javascript:submitInfo()" class="easyui-linkbutton"
								data-options="iconCls:'icon-submit'">修改个人信息</a></td>
					</tr>
				</table>
			</form>
		</div>

	</body>

</html>