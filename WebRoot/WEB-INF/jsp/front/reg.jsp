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
				<h3 class="baseH3">注册</h3>
				<hr>

				<div class="am-panel am-panel-default">
					<div class="am-panel-hd">用户注册</div>
					<div class="am-panel-bd">
						<c:if test="${(!empty setting.openReg)&& (setting.openReg==0)}">本站已关闭注册</c:if>
						<c:if
							test="${!((!empty setting.openReg)&& (setting.openReg==0)) }">
							<form method="post" class="am-form" id="dataform">
								<div class="am-input-group">
									<span class="am-input-group-label"><i
										class="am-icon-user am-icon-fw"></i></span> <input id="userName"
										name="userName" class="am-form-field" datatype="s4-16"
										placeholder="请输入用户名" errormsg="请输入正确的用户名(4-16位)" sucmsg="输入正确"
										nullmsg="请输入用户名">
								</div>
								<br />
								<div class="am-input-group">
									<span class="am-input-group-label"><i
										class="am-icon-lock am-icon-fw"></i></span> <input type="password"
										id="userPwd" name="userPwd" class="am-form-field"
										placeholder="请输入密码" datatype="*6-16"
										errormsg="请输入正确的密码(6-16位)！" sucmsg="输入正确" nullmsg="请输入密码">
								</div>
								<br>
								<c:if test="${setting.openInvite==1 }">
									<div class="am-input-group">
										<span class="am-input-group-label"><i
											class="am-icon-user am-icon-fw"></i></span> <input datatype="s32-32"
											id="invite" name="invite" class="am-form-field"
											placeholder="请输入邀请码" errormsg="请输入正确的邀请码(32位)" sucmsg="输入正确"
											nullmsg="请输入邀请码">
									</div>
									<br />
								</c:if>
								<div class="am-input-group">
									<span class="am-input-group-label"><i
										class="am-icon-lock am-icon-fw"></i></span><input datatype="n4-6"
										id="verCode" name="verCode" class="am-form-field"
										placeholder="请输入图形验证码" errormsg="请输入正确的验证码(4位)" sucmsg="输入正确"
										nullmsg="请输入验证码" style="width: 60%;float:left"> <img
										 onclick="refVerCode()"
													id="currVerCode"  alt="点击更换验证码"
										src="${basePath }VerificationServlet" height="100%">
								</div>
								<div class="am-form-group" style="height: 10px;">
									<span style="color: red;height: 20px;float: right" id="msg"></span>
								</div>
								<div class="am-cf">
									<button type="submit" name=""
										class="am-btn am-btn-success am-btn-xs">
										立即注册
									</button>
									<a class="am-btn am-btn-warning am-btn-xs" style="float: right"
										href="${basePath }login.${defSuffix}"> 已有账号,立即登录
									</a>
								</div>
							</form>
						</c:if>
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
function refVerCode() {
		var imgSrc = "${basePath}VerificationServlet?"
				+ Math.round(Math.random() * 1000000);
		setTimeout(function() {
			document.getElementById("currVerCode").src = imgSrc;
		}, 0);

	}
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
			url : 'doReg.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				refVerCode();
				if (json.code != 0) {
					alert(json.msg);
					return;
				}
				location.href = "${basePath}user/index.${defSuffix}";

			},
			error : function() {
				alert("系统繁忙");
			}

		});
	}
</script>
</html>
