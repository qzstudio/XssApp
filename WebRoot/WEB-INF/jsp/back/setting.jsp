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
					<strong class="am-text-primary am-text-lg">系统管理</strong> / <small>基本设置</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-4'}">
					网站信息设置<span class="am-icon-chevron-down am-fr"></span>
				</div>
				<div id="collapse-panel-4" class="am-panel-bd am-collapse am-in">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8"></div>
						<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">

							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label"><small>网站标题</small></label>
								<div class="am-u-sm-9">
									<input name="setting.id" type="hidden" value="${setting.id }" />
									<input type="text" id="user-name" value="${setting.siteName }"
										name="setting.siteName" datatype="*2-64" placeholder="请输入网站标题"
										errormsg="请输入正确的网站标题(2-64位)" sucmsg="输入正确" nullmsg="请输入网站标题">
								</div>
							</div>

							<div class="am-form-group">
								<label  class="am-u-sm-3 am-form-label"><small>网站关键字</small></label>
								<div class="am-u-sm-9">
									<input type="text" id="user-email"
										value="${setting.keywords }" name="setting.keywords"
										datatype="*2-100" placeholder="请输入网站关键字"
										errormsg="请输入正确的网站关键字(2-100位)" sucmsg="输入正确" nullmsg="请输入网站关键字">
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-phone" class="am-u-sm-3 am-form-label"><small>网站描述</small></label>
								<div class="am-u-sm-9">
									<input type="text" id="user-phone" 
										value="${setting.description }" name="setting.description"
										datatype="*2-200" placeholder="请输入网站描述"
										errormsg="请输入正确的网站描述(2-200位)" sucmsg="输入正确" nullmsg="请输入网站描述"
										>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-QQ" class="am-u-sm-3 am-form-label"><small>底部版权</small></label>
								<div class="am-u-sm-9">
									<input type="text" id="user-QQ"
										value="${setting.copyright }" name="setting.copyright"
											datatype="*2-100"  placeholder="请输入底部版权"
										errormsg="请输入正确的底部版权(2-100位)" sucmsg="输入正确" nullmsg="请输入底部版权"
										>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-QQ" class="am-u-sm-3 am-form-label"><small>开启注册</small></label>
								<div class="am-u-sm-9">
									<label class="am-checkbox-inline"> <input
										name="setting.openReg" id="private" type="radio" value="1"
										<c:if test="${empty setting.openReg}">checked</c:if>
										<c:if test="${setting.openReg==1 }">checked</c:if>> <label
										style="color: #7A7D7E" for="private">开启</label>
									</label> <label class="am-checkbox-inline"> <input
										name="setting.openReg" id="public" type="radio" value="0"
										<c:if test="${setting.openReg==0 }">checked</c:if>> <label
										style="color: #7A7D7E" for="public">关闭</label>
									</label>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-QQ" class="am-u-sm-3 am-form-label"><small>启用邀请码</small></label>
								<div class="am-u-sm-9">
									<label class="am-checkbox-inline"> <input
										name="setting.openInvite" id="private" type="radio" value="1"
										<c:if test="${setting.openInvite==1 }">checked</c:if>>
										<label style="color: #7A7D7E" for="private">开启</label>
									</label> <label class="am-checkbox-inline"> <input
										name="setting.openInvite" id="public" type="radio" value="0"
										<c:if test="${empty setting.openInvite}">checked</c:if>
										<c:if test="${setting.openInvite==0 }">checked</c:if>>
										<label style="color: #7A7D7E" for="public">关闭</label>
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button  class="am-btn am-btn-primary"
								 type="submit">保存修改</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
<jsp:include page="base/js.jsp" />
<script>
	$("#dataform").Validform({
		label : ".lable",
		showAllError : false,
		postonce : true,
		tiptype : 3,
		beforeSubmit : function(curform) {
			saveSetting();
			return false;
		}
	});
	function saveSetting() {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : 'saveSetting.${defSuffix}',
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
