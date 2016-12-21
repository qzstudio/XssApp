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
							<div class="am-panel-hd">创建模块</div>
							<div class="am-panel-bd">
								<form action="" class="am-form" id="dataform" data-am-validator>
									<input type="hidden" value=${module.id } name="id">
									<fieldset>
										<legend>模块及代码</legend>
										<div class="am-form-group">
											<label for="doc-vld-name-2"><small>模块名称：</small></label> <input type="text"
												id="doc-vld-name-2" name="title" datatype="*1-48"
												errormsg="请输入正确的模块名" sucmsg="输入正确" nullmsg="请输入模块名"
												placeholder="输入模块名称（至少 1 个字符）" value="${module.title }" />
										</div>
										<div class="am-form-group">
											<label for="doc-vld-ta-2"><small>模块描述：</small></label>
											<textarea id="doc-vld-ta-2" name="remark" >${module.remark }</textarea>
										</div>
										<div class="am-form-group">
											<label for="doc-vld-ta-2"><small>代码 （请在代码里面带上api接口参数：<strong
												style="color: red">{api}</strong>）</small>
											</label>
											<textarea id="doc-vld-ta-2" 
												style="height: 15%;" name="content" datatype="*1-819200"
												errormsg="请输入正确的模块代码" sucmsg="输入正确" nullmsg="请输入模块代码">${module.content }</textarea>
										</div>
										<c:if test="${(empty module)||module.user.id==curr_login_user.id}">
										<div class="am-form-group" style="height: 10px;">
											<span style="color: red;height: 20px;float: right" id="msg"></span>
										</div>
										<label class="am-checkbox-inline"> <input name="type" id="private"
											type="radio" value="1"
											<c:if test="${empty module.type }">checked</c:if>
											<c:if test="${module.type==1 }">checked</c:if> required>
											<label style="color: #7A7D7E"  for="private">私有</label>
										</label> <label class="am-checkbox-inline"> <input name="type" id="public"
											type="radio" value="0"
											<c:if test="${module.type==0 }">checked</c:if> required>
											 <label style="color: #7A7D7E"  for="public">公开</label>
										</label>
										<button type="submit" name=""
										class="am-btn am-btn-success am-btn-xs">
										&nbsp;保 存&nbsp;
										</button>
										
										</c:if>
									</fieldset>
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
		tiptype :3,
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
			url : 'saveModule.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
				if (json.code == 0) {
					location.href='${basePath }user/loadUserModule.${defSuffix}';
				}

			},
			error : function() {
				alert("系统繁忙");
			}

		});
	}
</script>
</html>
