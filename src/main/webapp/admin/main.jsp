<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>个人博客系统后台管理页面</title>
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

			function openTab(text, url, iconCls) {
				if ($("#tabs").tabs("exists", text)) {
					$("#tabs").tabs("select", text);
				} else {
					var content =
						"<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/admin/" +
						url + "'></iframe>";
					$("#tabs").tabs("add", {
						title: text,
						iconCls: iconCls,
						closable: true,
						content: content
					});
				}
			}



			function refreshSystem() {
				$.post("${pageContext.request.contextPath}/admin/system/refresh.action", function(result) {
					if (result.success) {
						$.messager.alert("系统提示", "刷新缓存成功");
					} else {
						$.messager.alert("系统提示", "刷新缓存失败");
					}
				})
			}

			function openPasswordModifyDialog() {
				$("#dlg").dialog("open").dialog("setTitle", "修改密码");
				url = "${pageContext.request.contextPath}/admin/blogger/saveNewPassword.action";
			}

			function saveNewPassword() {
				$("#fm").form("submit", {
					url: url,
					onSubmit: function() {
						if (!$(this).form("validate")) return false;
						var newPassword = $("#newPassword").val();
						var newPassword2 = $("#newPassword2").val();
						if (newPassword != newPassword2) return false;
						return true;
					},
					success: function(result) {
						var result = eval('(' + result + ')');
						if (result.success) {
							$.messager.alert("系统提示", "保存成功！");
							resetValue();
							$("#dlg").dialog("close");
						} else {
							$.messager.alert("系统提示", "保存失败！");
							return;
						}
					}
				});
			}


			function closeDialog() {
				$("#dlg").dialog("close");
				resetValue();
			}

			function resetValue() {
				$("#newPassword").val("");
				$("#newPassword2").val("");
				$("#orderNo").val("");
			}

			function logout() {
				$.messager.confirm("系统提示", "你确定要退出系统吗", function(r) {
					if (r) {
						window.location.href = "${pageContext.request.contextPath}/admin/blogger/logout.action"
					}
				});
			}
		</script>
	</head>
	<body class="easyui-layout">
		<div region="north" style="height: 60px;background-color: #E0ECFF;overflow:hidden">
			<table style="padding: 5px" width="100%">
				<tr>
					<td style="padding-top: 16px" valign="middle" align="left" width="50%">
						<font size="3">&nbsp;&nbsp;<strong>个人博客系统</strong></font>
					</td>
					<td style="padding-top: 16px" valign="middle" align="right" width="50%">
						<font size="3">&nbsp;&nbsp;<strong>欢迎：</strong>arth_saer</font>
					</td>
				</tr>
			</table>
		</div>
		<div region="center">
			<div class="easyui-tabs" fit="true" border="false" id="tabs">
				<div title="首页" data-options="iconCls:'icon-home'">
					<div align="center" style="padding-top: 100px">
						<font color="red" size="10">欢迎使用</font>
					</div>
				</div>
			</div>
		</div>
		<div region="west" style="width: 200px" title="导航菜单" split="true">
			<div class="easyui-accordion" data-options="fit:true,border:false">

				<div title="常用操作菜单" data-options="iconCls:'icon-cycz'" style="padding:10px;">
					<a href="javascript:openTab('写博客','writeBlog.jsp','icon-writeblog')" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px;">写博客</a>
					<a href="javascript:openTab('评论审核','commentReview.jsp','icon-review')" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-review'" style="width: 150px">评论审核</a>
				</div>

				<div title="博客管理" data-options="iconCls:'icon-bkgl'" style="padding:10px;">
					<a href="javascript:openTab('写博客','writeBlog.jsp','icon-writeblog')" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px;">写博客</a>
					<a href="javascript:openTab('博客信息管理','blogManage.jsp','icon-bkgl')" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-bkgl'" style="width: 150px;">博客信息管理</a>
				</div>
				<div title="博客类别管理" data-options="iconCls:'icon-bklb'" style="padding:10px">
					<a href="javascript:openTab('博客类别信息管理','blogTypeManage.jsp','icon-bklb')" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-bklb'" style="width: 150px;">博客类别信息管理</a>
				</div>
				<div title="评论管理" data-options="iconCls:'icon-plgl'" style="padding:10px">
					<a href="javascript:openTab('评论审核','commentReview.jsp','icon-review')" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-review'" style="width: 150px">评论审核</a>
					<a href="javascript:openTab('评论信息管理','commentManage.jsp','icon-plgl')" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-plgl'" style="width: 150px;">评论信息管理</a>
				</div>
				<div title="个人信息管理" data-options="iconCls:'icon-grxx'" style="padding:10px">
					<a href="javascript:openTab('修改个人信息','modifyInfo.jsp','icon-grxxxg')" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-grxxxg'" style="width: 150px;">修改个人信息</a>
				</div>
				<div title="系统管理" data-options="iconCls:'icon-system'" style="padding:10px">
					<a href="javascript:openTab('友情链接管理','linkManage.jsp','icon-link')" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-link'" style="width: 150px">友情链接管理</a>
					<a href="javascript:openPasswordModifyDialog()" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-modifyPassword'" style="width: 150px;">修改密码</a>
					<a href="javascript:refreshSystem()" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-refresh'" style="width: 150px;">刷新系统缓存</a>
					<a href="javascript:logout()" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-exit'" style="width: 150px;">安全退出</a>
				</div>
			</div>
		</div>
		<div region="south" style="height: 25px;padding: 5px" align="center">

		</div>

		<div id="dlg" class="easyui-dialog" style="width:500px;height:180px;padding: 10px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" method="post">
				<table cellspacing="8px">
					<tr>
						<td>用户名：</td>
						<td><input type="text" id="id" name="id" value="${currentBlogger.id}" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td>新密码：</td>
						<td><input type="password" id="newPassword" name="newPassword" class="easyui-validatebox"
								required="true" /></td>
					</tr>
					<tr>
						<td>确认新密码：</td>
						<td><input type="password" id="newPassword2" name="newPassword2" class="easyui-validatebox"
								required="true" /></td>
					</tr>

				</table>
			</form>
		</div>

		<div id="dlg-buttons">
			<a href="javascript:saveNewPassword()" class="easyui-linkbutton" iconCls="icon-ok">修改</a>
			<a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
		</div>

	</body>
</html>