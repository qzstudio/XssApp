<%@page import="com.xss.web.util.DateUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<!doctype html>
<html class="no-js">
<head>
<jsp:include page="base/head.jsp" />
</head>
<body>
	<form class="am-form am-form-horizontal" method="post" id="dataform">
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">权限管理</strong> / <small>角色编辑</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-4'}">
					角色编辑<span class="am-icon-chevron-down am-fr"></span>
				</div>
				<div id="collapse-panel-4" class="am-panel-bd am-collapse am-in">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8"></div>
						<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label"><small>角色名称</small></label>
								<div class="am-u-sm-9">
									<input name="id" type="hidden" value="${role.id }" /> <input
										type="text" id="user-name" placeholder="菜单标题"
										value="${role.name }" name="name"> <small>请输入角色名称</small>
								</div>
							</div>
							<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-primary"
								onclick="save()">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	
	</form>
</body>
<jsp:include page="base/js.jsp" />
<script>
	function save() {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : 'roleSave.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
				if (json.code == 0) {
					location.href = '${basePath }admin/roleList.${defSuffix}';
				}

			},
			error : function() {
				alert("系统繁忙");
			}

		});
	}
</script>
</html>
