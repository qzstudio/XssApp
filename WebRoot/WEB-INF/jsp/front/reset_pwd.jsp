<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<html>
<head lang="en">
<jsp:include page="../base/head.jsp" />
</head>
<body style="text-align:center;">

	<div style="margin:0px auto">
		<jsp:include page="../base/nav.jsp" />
		<div class="am-container" >
			<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
				<h3>重置用户密码</h3>
				<hr>

				<div class="am-panel am-panel-default">
					<div class="am-panel-hd">找回密码</div>
					<div class="am-panel-bd">
						<form method="post" class="am-form" id="dataform" onsubmit="return false;">
							<div class="am-input-group">
								<span class="am-input-group-label"><i
									class="am-icon-user am-icon-fw"></i></span> <input 
									id="userName" name="userName" class="am-form-field"
									placeholder="请输入用户名/手机/邮箱" datatype="*4-32" errormsg="请输入正确的用户名(4-16位)" sucmsg="输入正确"
									nullmsg="请输入用户名">
									
							</div>
							<br>
							<div class="am-input-group">
							<span class="am-input-group-label"><i
									class="am-icon-lock am-icon-fw"></i></span><input 
									id="verCode" name="verCode" class="am-form-field"
									datatype="n4-6" placeholder="请输入图形验证码" errormsg="请输入正确的验证码(4位)" sucmsg="输入正确"
									nullmsg="请输入验证码" style="width: 60%;float:left">
									<img onclick="refVerCode()"
													id="currVerCode" alt="点击更换验证码" src="${basePath }VerificationServlet" height="100%">
							</div>
							<br>
							<div class="am-input-group">
							<button type="submit" name=""
										class="am-btn am-btn-success am-btn-xs">
										发送验证码
								</button>
								<span id="msg"></span>
							</div>
							<br />
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
			url : 'sendVerCode.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				if (json.code != 0) {
					alert(json.msg);
					$("img#codeimg")[0].src="${basePath }VerificationServlet?"+Math.round(Math.random()*1000000);
					
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
