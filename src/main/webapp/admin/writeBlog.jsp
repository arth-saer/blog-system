<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客管理页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
	var ue = UE.getEditor("editor");
	function submitBlog(){
		var title = $("#title").val();
		var typeId = $("#typeId").combobox("getValue");
		var content = ue.getContent();
		var contentNoTag = ue.getContentTxt();
		var summary = ue.getContentTxt().substr(0, 150);
		var keyword = $("#keyword").val();
		if(title == null || title == ''){
			$.messager.alert("系统提示", "请输入标题");
		}else if(typeId == null || typeId == ''){
			$.messager.alert("系统提示", "请输入博客类型");
		}else if(content == null || content == ''){
			$.messager.alert("系统提示", "请输入内容");
		}else{
			$.post("${pageContext.request.contextPath}/admin/blog/save.action",
				{"title":title, "typeId":typeId, "content":content, "contentNoTag":contentNoTag,
				"summary":summary, "keyword":keyword},
				function(result){
					if(result.success){
						$.messager.alert("系统提示", "博客发布成功");
					}else{
						$.messager.alert("系统提示", "博客发布失败");
					}
				});
		}
	}
</script>

</head>

<body style="margin: 10px">

<div id="p" class="easyui-panel" title="编写博客" style="padding: 10px">
<table cellspacing="20px">
	<tr>
		<td width="80px">博客标题：</td>
		<td><input type="text" id="title" name="title" style="width: 400px"/></td>
	</tr>
	<tr>
		<td width="80px">博客类别：</td>
		<td>
		    <select id="typeId" class="easyui-combobox" name="typeId" style="width: 200px" editable="false" panelHeight="auto">
                <option value="">请选择博客类别</option>
                <c:forEach var="blogType" items="${blogTypeList}">
                    <option value="${blogType.id}">${blogType.typeName}</option>
                </c:forEach>
		    </select>
		</td>
	</tr>
	<tr>
		<td width="80px">博客内容：</td>
		<td>
			<script id="editor" type="text/plain" style="width: 100%; height: 400px;"></script>
		</td>
	</tr>
	<tr>
		<td width="80px">博客关键字：</td>
		<td><input type="text" id="keyword" name="keyword" style="width: 400px; height: 30px;"/>
		&nbsp;(多个关键字中间用空格隔开)</td>
	</tr>
	<tr>
		<td></td>
		<td><a href="javascript:submitBlog()" class="easyui-linkbutton" data-options="iconCls:'icon-submit'">发布博客</a></td>
	</tr>
</table>
</div>

</body>

</html>