<%@page import="com.xss.web.util.DateUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<!doctype html>
<html class="no-js">
<head>
<jsp:include page="base/head.jsp" />
</head>
<body>
	<form class="am-form am-form-horizontal" method="post" id="dataform"
		name="searchForm">
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">内容管理</strong> / <small>信封列表</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-base'}">
					信封列表<span class="am-icon-chevron-down am-fr" style="display: none"></span>
				</div>
				<div id="collapse-panel-base" class="am-panel-bd am-collapse am-in">
					<ul class="am-list am-list-static am-list-border">
						<li><span class="nowrap">
								<div class="am-panel am-panel-default" style="width: auto">
									<div class="am-panel-bd">
										用户名:<input name="project.user.userName"
											class="am-form-input  am-radius" type="email"
											style="width: auto;display: -webkit-inline-box"
											value="${project_user_userName }" /> &nbsp;&nbsp;项目名:<input
											name="project.title" class="am-form-input am-radius"
											type="email"
											style="width:
										auto;display: -webkit-inline-box"
											value="${project_title }" /> &nbsp;&nbsp;模块名：<input
											name="project.module.title" class="am-form-input am-radius"
											type="email"
											style="width:
										auto;display: -webkit-inline-box"
											value="${project_module_title }" /> &nbsp;&nbsp;来源地址：<input
											name="refUrl" type=text class="am-form-input  am-radius"
											style="width: auto;display: -webkit-inline-box"
											value="${refUrl }" /> <a href="javascript:indexPage()"
											class="am-btn am-btn-default am-fr ">查询</a>
									</div>
								</div>
						</span></li>
					</ul>
					<table class="am-table am-table-bordered">
						<thead>
							<tr>
								<th>ID</th>
								<th>所属用户</th>
								<th>所属项目</th>
								<th>所属模版</th>
								<th>来源地址</th>
								<th>IP地址</th>
								<th>更新时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<c:if test="${empty pager.pageData}"><tr><td  colspan="8" style="text-align:center ">暂无数据</td></tr></c:if>
							<c:forEach items="${pager.pageData }" var="letter">
								<tr>
									<td>${letter.id }</td>
									<td>${letter.project.user.userName }</td>
									<td>${letter.project.title }</td>
									<td>${letter.project.module.title}</td>
									<td>${letter.refUrl }</td>
									<td>${letter.ip }</td>
									<td>${letter.updateTime}</td>
									<td><a
										href="${basePath }admin/letterEdit.${defSuffix}?id=${letter.id }">查看</a>
										<a href="#" onclick="delDate(${letter.id })">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
						<thead>
							<tr class="am-disabled">
								<th colspan="8"><jsp:include page="../base/page.jsp" /></th>
							</tr>
						</thead>
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
			data : 'id=' + id,
			url : 'letterDel.${defSuffix}',
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
