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
			<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
				<h3>登录</h3>
				<hr>

				<div class="am-panel am-panel-default">
					<div class="am-panel-hd">用户登录</div>
					<div class="am-panel-bd">
						<form method="post" class="am-form" id="dataform">
							<div class="am-input-group">
								<span class="am-input-group-label"><i
									class="am-icon-user am-icon-fw"></i></span> <input datatype="*4-32"
									id="userName" name="userName" class="am-form-field"
									placeholder="请输入用户名/手机/邮箱" errormsg="请输入正确的用户名(4-16位)"
									sucmsg="输入正确" nullmsg="请输入用户名">
							</div>
							<br />
							<div class="am-input-group">
								<span class="am-input-group-label"><i
									class="am-icon-lock am-icon-fw"></i></span> <input datatype="*6-16"
									type="password" id="userPwd" name="userPwd"
									class="am-form-field" placeholder="请输入密码"
									errormsg="请输入正确的密码(6-16位)！" sucmsg="输入正确" nullmsg="请输入密码">
							</div>
							<br>
							<div class="am-form-group" style="height: 10px;">
								<span style="color: red;height: 20px;float: right" id="msg"></span>
							</div>
							<div class="am-cf">
								<button type="submit" name=""
									class="am-btn am-btn-success am-btn-xs">
									&nbsp;登 录&nbsp;
								</button>
								<a>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</a>
								<a href="${basePath }reg.${defSuffix}">
									<button type="button" name=""
										class="am-btn am-btn-warning am-btn-xs">
										&nbsp;注 册&nbsp;
									</button>
								</a> <a class="am-btn am-btn-danger am-btn-xs" style="float: right"
									href="${basePath }resetPwd.${defSuffix}"> 忘记密码
								</a>
							</div>
						</form>
					</div>
				</div>

				<hr>
			</div>
		</div>
		<jsp:include page="../base/foot.jsp" />
	</div>
	<jsp:include page="../base/js.jsp" />
</body>
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
			submitForm(0);
			return false;
		}
	});
	function submitForm(type) {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : 'doLogin.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				if (json.code != 0) {
					if(type==0){
						alert(json.msg);
					}
					return;
				}
				localStorage.setItem("userName", $("#userName").val());
				localStorage.setItem("userPwd", $("#userPwd").val());
				location.href = "${basePath}user/index.${defSuffix}";

			},
			error : function() {
				alert("系统繁忙");
			}

		});
	}
</script>
<c:if test="${action!='loginOut' }">
<script src="${basePath}assets/js/sys_login.js"></script>
</c:if>
</html>
