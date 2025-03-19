<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>评论审核页面</title>
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
		<script type="text/javascript">
			function getBlogTitle(val, row) {
				return val.title;
			}

			function openCommentReview(status) {
				var selectedRows = $("#dg").datagrid("getSelections");
				if (selectedRows.length < 1) {
					$.messager.alert("系统提示", "请至少选择一条数据！");
					return;
				}
				var strIds = [];
				for (var i = 0; i < selectedRows.length; i++) strIds.push(selectedRows[i].id);
				var ids = strIds.join(',');
				$.messager.confirm("系统提示", "你确定要审核这<font color=red>" + selectedRows.length + "</font>条数据吗", function(r) {
					if (r) {
						$.post("${pageContext.request.contextPath}/admin/comment/review.action", {
							ids: ids, status:status
						}, function(result) {
							if (result.success) {
								$.messager.alert("系统提示", "审核成功");
								$("#dg").datagrid("reload");
							} else {
								$.messager.alert("系统提示", "审核失败");
							}
						}, "json");
					}
				});
			}
		</script>
	</head>
	<body style="margin: 10px">
		<table id="dg" title="评论审核" class="easyui-datagrid" fitcolumns="true" pagination="true" rownumbers="true" pageSize="30"
			url="${pageContext.request.contextPath}/admin/comment/list.action?status=0" fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th field="cb" checkbox="true" align="center"></th>
					<th field="id" width="30" align="center">编号</th>
					<th field="blog" width="150" align="center" formatter="getBlogTitle">博客标题</th>
					<th field="content" width="200" align="center">内容</th>
					<th field="commentDateStr" width="50" align="center">评论日期</th>
				</tr>
			</thead>
		</table>

		<div id="tb">
			<a href="javascript:openCommentReview(1)" class="easyui-linkbutton" iconCls="icon-ok" plain="true">通过</a>
			<a href="javascript:openCommentReview(2)" class="easyui-linkbutton" iconCls="icon-no" plain="true">不通过</a>

		</div>
	</body>
</html>