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
					<strong class="am-text-primary am-text-lg">系统管理</strong> / <small>系统发信邮箱管理</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-4'}">
					系统发信邮箱管理<span class="am-icon-chevron-down am-fr"
						style="display: none"></span><a href="${basePath }admin/emailEdit.${defSuffix}"
						class="am-btn am-btn-default am-round am-fr am-btn-xs"
						style="float: right">添加邮箱</a>
				</div>
				<div id="collapse-panel-4" class="am-panel-bd am-collapse am-in">
					<table class="am-table am-table-bordered">
						<thead>
							<tr>
								<th>ID</th>
								<th>服务器</th>
								<th>发信邮箱</th>
								<th>发信密码</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<c:if test="${empty emails}"><tr><td  colspan="5" style="text-align:center ">暂无数据</td></tr></c:if>
							<c:forEach items="${emails }" var="email">
								<tr>
									<td>${email.id }</td>
									<td>${email.smtp }</td>
									<td>${email.email }</td>
									<td>${email.password }</td>
									<td>${email.activite==0?'不可用':'可用' }</td>
									<td><a
										href="${basePath }admin/emailEdit.${defSuffix}?id=${email.id }">编辑</a>
										<a href="#" onclick="delDate(${email.id })">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
<jsp:include page="base/js.jsp" />
<script>
	function delDate(id) {
	if (!confirm("数据删除后将无法恢复,是否继续操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : 'id='+id,
			url : 'emailDel.${defSuffix}',
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
