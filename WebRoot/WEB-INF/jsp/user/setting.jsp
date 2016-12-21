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
				<div class="am-panel am-panel-default">
					<div class="am-panel-hd">个人设置</div>
					<div class="am-panel-bd">
						<form action="" id="dataform" class="am-form" data-am-validator>
							<fieldset>
								<legend>${curr_login_user.userName }</legend>
								<div class="am-input-group" style="width: 100%">
						<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4" style="float:right">

							<div class="am-input-group">
								<label for="user-name" class="am-u-sm-3 am-form-label"><small>邮箱</small></label>
								<div class="am-u-sm-9">
									<input datatype="e" name="email"
												class="am-form-field Validform_error" placeholder="请输入邮箱"
												errormsg="请输入正确的邮箱" sucmsg="输入正确" nullmsg="请输入邮箱"
												value="${curr_login_user.email }">
								</div>
							</div>
							<br/>							
						<div class="am-input-group">
								<label for="user-name" class="am-u-sm-3 am-form-label"><small>手机</small></label>
								<div class="am-u-sm-9">
									<input datatype="m" name="mobile"
												class="am-form-field Validform_error" placeholder="请输入手机号"
												errormsg="请输入正确的手机号" sucmsg="输入正确" nullmsg="请输入手机号"
												value="${curr_login_user.mobile }">
								</div>
							</div>
						</div>							
								</div>
								<br><br>
								<div class="am-form-group" style="height: 10px;">
									<span style="color: red;height: 20px;float: right" id="msg"></span>
								</div>
								<button type="submit" name=""
										class="am-btn am-btn-success am-btn-xs">
										&nbsp;保 存&nbsp;
								</button>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../base/foot.jsp" />
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
			url : 'saveUserInfo.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
				if (json.code == 0) {
					location.reload(true);
				}

			},
			error : function() {
				alert("系统繁忙");
			}

		});
	}
</script>
</html>
