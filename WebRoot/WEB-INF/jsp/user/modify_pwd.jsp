<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<html>
<head lang="en">
<jsp:include page="../base/head.jsp" />
</head>
<body style="text-align:center;">
	<div style="margin:0px auto">
		<jsp:include page="../base/nav.jsp" />
		<div class="am-container">
			<jsp:include page="../base/left.jsp" />
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
				<div class="am-g">
					<div class="am-u-md-12">
						<div class="am-panel am-panel-default">
							<div class="am-panel-hd">修改密码</div>
							<div class="am-panel-bd">
								<form method="post" class="am-form" id="dataform"
								onsubmit="return false;">
								<div class="am-input-group">
									<span class="am-input-group-label"><i
										class="am-icon-lock am-icon-fw"></i></span> <input datatype="*6-16"
										type="password" id="oldPwd" name="oldPwd"
										class="am-form-field" placeholder="请输入旧密码"
										errormsg="请输入正确的密码(6-16位)！" sucmsg="输入正确" nullmsg="请输入旧密码">
								</div>

								<br />
								<div class="am-input-group">
									<span class="am-input-group-label"><i
										class="am-icon-lock am-icon-fw"></i></span> <input datatype="*6-16"
										type="password" id="userPwd" name="userPwd"
										class="am-form-field" placeholder="请输入新密码"
										errormsg="请输入正确的密码(6-16位)！" sucmsg="输入正确" nullmsg="请输入新密码">
								</div>

								<br />
								<div class="am-input-group">
									<span class="am-input-group-label"><i
										class="am-icon-lock am-icon-fw"></i></span> <input id="userPwdAgain"
										type="password" class="am-form-field" datatype="*"
										recheck="userPwd" errormsg="两次输入的密码不一致" sucmsg="输入正确"
										nullmsg="请重新输入新密码" placeholder="请重新输入新密码">
								</div>
								
								<br>
								<div class="am-input-group">
								<button type="submit" name=""
									class="am-btn am-btn-success am-btn-xs">
									&nbsp;确认修改&nbsp;
								</button>
									<span id="msg"></span>
								</div>
								<br />
							</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../base/foot.jsp" />
	</div>
	<jsp:include page="../base/js.jsp" />
</body>
<style>
.am-form-group {
	text-align: left;
}
</style>
<script>
	$("#dataform").Validform({
		label : ".lable",
		showAllError : false,
		postonce : true,
		tiptype : function(msg, o, cssctl) {
			var objtip = $("#msg");
			cssctl(objtip, o.type);
			objtip.text(msg);
		},
		beforeSubmit : function(curform) {
			submitForm();
			return false;
		}
	});
	function submitForm() {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : 'doModifyPwd.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
				if (json.code == 0) {
					location.href='${basePath }user/index.${defSuffix}';
				}
			},
			error : function() {
				alert("系统繁忙");
			}

		});
	}
</script>
</html>
