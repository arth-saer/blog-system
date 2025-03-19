<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<script type="text/javascript">
			function getTypeName(val, row) {
				return val.typeName;
			}

			function getHref(val, row) {
            	return "<a target = '_blank' href='${pageContext.request.contextPath}/blog/" + row.id + ".html'>" + val + "</a>";
            }

			function searchBlog() {
				$("#dg").datagrid("load", {
					"title": $("#s_title").val()
				});
			}

			function openBlogUpdate() {
				var selectedRows = $("#dg").datagrid("getSelections");
				if (selectedRows.length != 1) {
					$.messager.alert("系统提示", "请选择一条数据！");
					return;
				}
				var selectedRow = selectedRows[0];
				window.parent.openTab("修改博客", "modifyBlog.jsp?id="+selectedRow.id, "icon-writeBlog");
			}

			function openBlogDelete(){
            		var selectedRows = $("#dg").datagrid("getSelections");
            		if(selectedRows.length < 1){
            			$.messager.alert("系统提示", "请至少选择一条数据！");
            			return;
            		}
            		var strIds = [];
            		for(var i = 0; i < selectedRows.length; i++) strIds.push(selectedRows[i].id);
            		var ids = strIds.join(',');
            		$.messager.confirm("系统提示", "你确定要删除这<font color=red>" + selectedRows.length + "</font>条数据吗", function(r){
            			if(r){
            				$.post("${pageContext.request.contextPath}/admin/blog/delete.action", {ids:ids}, function(result){
            					if(result.success){
            						$.messager.alert("系统提示", "删除成功");
            						$("#dg").datagrid("reload");
            					}else{
            						$.messager.alert("系统提示", "删除失败");
            					}
            				}, "json");
            			}
            		});
            	}

		</script>


	</head>

	<body style="margin: 10px">

		<table id="dg" title="博客类别管理" class="easyui-datagrid" fitcolumns="true" pagination="true" rownumbers="true" pageSize="30"
			url="${pageContext.request.contextPath}/admin/blog/list.action" fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th field="cb" checkbox="true" align="center"></th>
					<th field="id" width="30" align="center">编号</th>
					<th field="title" width="200" align="center" formatter="getHref">标题</th>
					<th field="createDateStr" width="70" align="center">发表日期</th>
					<th field="typeName" width="50" align="center">类别</th>
				</tr>
			</thead>
		</table>

		<div id="tb">
			<div>
				<a href="javascript:openBlogUpdate()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
				<a href="javascript:openBlogDelete()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			</div>
			<div>
				&nbsp;标题：&nbsp;<input type="text" id="s_title" size="21" />
				<a href="javascript:searchBlog()" class="easyui-linkbutton" iconCls="icon-search" plain="true"></a>
			</div>
		</div>

	</body>

</html>