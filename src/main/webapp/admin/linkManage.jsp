<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>友情链接管理页面</title>
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
			var url;

			function openLinkInsertDialog() {
				$("#dlg").dialog("open").dialog("setTitle", "添加友情链接信息");
				url = "${pageContext.request.contextPath}/admin/link/save.action";
			}


			function openLinkUpdateDialog() {
				var selectedRows = $("#dg").datagrid("getSelections");
				if (selectedRows.length != 1) {
					$.messager.alert("系统提示", "请选择一条数据！");
					return;
				}
				var selectedRow = selectedRows[0];
				$("#dlg").dialog("open").dialog("setTitle", "修改友情链接信息");
				$("#fm").form("load", selectedRow);
				url = "${pageContext.request.contextPath}/admin/link/save.action?id=" + selectedRow.id;
			}

			function openLinkDelete() {
				var selectedRows = $("#dg").datagrid("getSelections");
				if (selectedRows.length < 1) {
					$.messager.alert("系统提示", "请至少选择一条数据！");
					return;
				}
				var strIds = [];
				for (var i = 0; i < selectedRows.length; i++) strIds.push(selectedRows[i].id);
				var ids = strIds.join(',');
				console.log("删除请求参数 ids:", ids);
				$.messager.confirm("系统提示", "你确定要删除这<font color=red>" + selectedRows.length + "</font>条数据吗", function(r) {
					if (r) {
						$.post("${pageContext.request.contextPath}/admin/link/delete.action", {
							ids: ids
						}, function(result) {
							if (result.success) {
								$.messager.alert("系统提示", "删除成功");
								$("#dg").datagrid("reload");
							} else {
								$.messager.alert("系统提示", "删除失败");
							}
						});
					}
				});
			}


			function closeLinkDialog() {
				$("#dlg").dialog("close");
				resetValue();
			}



			function saveLink() {
				$("#fm").form("submit", {
					url: url,
					onSubmit: function() {
						return $(this).form("validate");
					},

					success: function(result) {
						var result = eval('(' + result + ')');
						if (result.success) {
							$.messager.alert("系统提示", "保存成功！");
							resetValue();
							$("#dlg").dialog("close");
							$("#dg").datagrid("reload");
						} else {
							$.messager.alert("系统提示", "保存失败！");
							return;
						}
					}
				});
			}

			function resetValue() {
				$("#linkName").val("");
				$("#url").val("");
				$("#orderNo").val("");
			}
		</script>


	</head>

	<body style="margin: 1px">
		<table id="dg" title="友情链接管理" class="easyui-datagrid" fitcolumns="true" pagination="true" rownumbers="true" pageSize="30"
			url="${pageContext.request.contextPath}/admin/link/list.action" fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th field="cb" checkbox="true" align="center"></th>
					<th field="id" width="100" align="center">编号</th>
					<th field="linkName" width="250" align="center">友情链接名称</th>
					<th field="url" width="100" align="center">url</th>
					<th field="orderNo" width="250" align="center">排序序号</th>
				</tr>
			</thead>
		</table>

		<div id="tb">
			<a href="javascript:openLinkInsertDialog()" class="easyui-linkbutton" iconCls="icon-add"
				plain="true">添加</a>
			<a href="javascript:openLinkUpdateDialog()" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true">修改</a>
			<a href="javascript:openLinkDelete()" class="easyui-linkbutton" iconCls="icon-remove"
				plain="true">删除</a>
		</div>

		<div id="dlg" class="easyui-dialog" style="width:500px;height:180px;padding: 10px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" method="post">
				<table cellspacing="8px">
					<tr>
						<td>友情链接名称</td>
						<td><input type="text" id="linkName" name="linkName" class="easyui-validatebox"
								required="true" /></td>
					</tr>
					<tr>
						<td>url</td>
						<td><input type="text" id="url" name="url" class="easyui-validatebox"
								required="true" /></td>
					</tr>
					<tr>
						<td>排序序号</td>
						<td><input type="text" id="orderNo" name="orderNo" class="easyui-validatebox"
								required="true" />(类别从小到大排序)</td>
					</tr>

				</table>
			</form>
		</div>

		<div id="dlg-buttons">
			<a href="javascript:saveLink()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="javascript:closeLinkDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
		</div>

	</body>

</html>