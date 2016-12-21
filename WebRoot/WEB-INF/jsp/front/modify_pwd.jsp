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
				<h3>重置用户密码</h3>
				<hr>
				<div class="am-panel am-panel-default">
					<div class="am-panel-hd">重置密码</div>
					<div class="am-panel-bd">
						<c:if test="${msg.code==0 }">
							<form method="post" class="am-form" id="dataform"
								onsubmit="return false;">
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
									<span class="am-input-group-label"><i
										class="am-icon-user am-icon-fw"></i></span><input datatype="n4-6"
										id="verCode" name="verCode" class="am-form-field"
										placeholder="请输入图形验证码" errormsg="请输入正确的验证码(4位)" sucmsg="输入正确"
										nullmsg="请输入验证码" style="width: 60%;float:left"> <img
										alt="点击更换验证码" src="${basePath }VerificationServlet"
										height="100%">
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
						</c:if>
						<c:if test="${msg.code!=0 }">
					<div class="am-cf">
							<label style="align: center">${msg.msg }</label>
							<a class="am-btn am-btn-success am-round" style="float: right"
								href="${basePath }resetPwd.${defSuffix}"> <i
								class="am-icon-spinner am-icon-spin"></i>返回
							</a>
					</div>
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
			url : 'doModify.${defSuffix}',
			timeout : 60000,
			success : function(json) {
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
