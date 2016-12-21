<%@page import="com.xss.web.util.DateUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
					<strong class="am-text-primary am-text-lg">系统管理</strong> / <small>发信邮箱编辑</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-4'}">
					发信邮箱编辑<span class="am-icon-chevron-down am-fr"></span>
				</div>
				<div id="collapse-panel-4" class="am-panel-bd am-collapse am-in">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8"></div>
						<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">

							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label"><small>邮箱服务器</small></label>
								<div class="am-u-sm-9">
									<input name="id" type="hidden" value="${email.id }" /> <input
										type="text" id="user-name" placeholder="邮箱服务器"
										value="${email.smtp }" name="smtp"> <small>请输入邮箱服务器</small>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-email" class="am-u-sm-3 am-form-label"><small>发信邮箱</small></label>
								<div class="am-u-sm-9">
									<input type="email" id="user-email" placeholder="请输入发信邮箱"
										value="${email.email }" name="email"> <small>请输入发信邮箱</small>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-phone" class="am-u-sm-3 am-form-label"><small>邮箱密码</small></label>
								<div class="am-u-sm-9">
									<input type="email" id="user-phone" placeholder="邮箱密码"
										value="${email.password }" name="password"> <small>请输入邮箱密码</small>
								</div>
							</div>
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
			url : 'emailSave.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
				if (json.code == 0) {
					location.href='${basePath }admin/sysEmailAdmin.${defSuffix}';
				}

			},
			error : function() {
				alert("系统繁忙");
			}

		});
	}
</script>
</html>
