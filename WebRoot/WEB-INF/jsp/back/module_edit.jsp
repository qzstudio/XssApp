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
					<strong class="am-text-primary am-text-lg">内容管理</strong> / <small>模版编辑</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-4'}">
					模版编辑<span class="am-icon-chevron-down am-fr"></span>
				</div>
				<div id="collapse-panel-4" class="am-panel-bd am-collapse am-in">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8"></div>
						<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">

							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label"><small>模版名称</small></label>
								<div class="am-u-sm-9">
									<input name="id" type="hidden" value="${module.id }" /> <input
										type="text" id="user-name" placeholder="模版名称"
										value="${module.title }" name="title"> <small>请输入模版名称</small>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-email" class="am-u-sm-3 am-form-label"><small>模版描述</small></label>
								<div class="am-u-sm-9">
									<textarea id="doc-vld-ta-2" name="remark" placeholder="请输入项目描述">${module.remark }</textarea>
									<small>请输入模版描述</small>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-email" class="am-u-sm-3 am-form-label"><small>模版代码</small></label>
								<div class="am-u-sm-9">
									<textarea id="doc-vld-ta-2" name="content" rows="10"
										placeholder="请输入模版代码">${module.content }</textarea>
									<small>请输入模版代码</small>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-email" class="am-u-sm-3 am-form-label"><small>模版类型</small></label>
								<div class="am-u-sm-9">
									<label class="am-checkbox-inline"> <input name="type"
										id="private" type="radio" value="1"
										<c:if test="${empty module.type }">checked</c:if>
										<c:if test="${module.type==1 }">checked</c:if> required>
										<label style="color: #7A7D7E" for="private">私有</label>
									</label> <label class="am-checkbox-inline"> <input name="type"
										id="public" type="radio" value="0"
										<c:if test="${module.type==0 }">checked</c:if> required>
										<label style="color: #7A7D7E" for="public">公开</label>
									</label> 
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
		$
				.ajax({
					type : "POST",
					dataType : 'json',
					data : $("#dataform").serialize(),
					url : 'moduleSave.${defSuffix}',
					timeout : 60000,
					success : function(json) {
						alert(json.msg);
						if (json.code == 0) {
							location.href = '${basePath }admin/moduleList.${defSuffix}';
						}

					},
					error : function() {
						alert("系统繁忙");
					}

				});
	}
</script>
</html>
