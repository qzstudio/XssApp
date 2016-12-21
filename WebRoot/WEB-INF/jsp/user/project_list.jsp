<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld"%>
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
				<form id="searchForm" name="searchForm" method="post">
					<table
						class="am-table am-table-bordered am-table-radius am-table-striped">

						<thead>
							<tr class="am-disabled ">
								<th colspan="6">
									<div class="am-cf am-padding">
										<div class="am-fl am-cf">
											<strong class="am-text-primary am-text-lg">XssApp</strong> /
											<small>项目列表</small>
										</div>
									</div>
								</th>
							</tr>
						</thead>
						<thead>
							<tr class="am-disabled ">
								<th>名称</th>
								<th>模块</th>
								<th>代码</th>
								<th>信封数</th>
								<th class="timmer">时间</th>
								<th>管理</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty projects}">
								<tr>
									<td colspan="6" style="text-align: center"><span
										class="am-avg-sm-4 am-thumbnails">暂无数据</span></td>
								</tr>
							</c:if>
							<c:forEach items="${projects }" var="project">
								<tr>
									<td>${project.title }</td>
									<td>${project.module.title }<c:if
											test="${empty project.module }">
											<span style="color: red">无模块</span>
										</c:if>
									</td>

									<td>&lt;script src=${(empty project.sortUri)?project.uri:project.sortUri }&gt;&lt;/script&gt;</td>
									<td><a class="am-btn am-btn-link"
										href="${basePath }user/loadLetters.${defSuffix}?id=${project.id}">${project.count }条</a></td>
									<td  class="timmer"><fmt:formatDate
												value="${project.updateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>

									<td><a class="nowrap"
										href="${basePath }user/loadLetters.${defSuffix}?id=${project.id}">信封</a>
										<a class="nowrap"
										href="${basePath }user/projectEdit.${defSuffix}?id=${project.id}">编辑</a>
										<a class="nowrap" href="#" onclick="delProject(${project.id})">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="../base/foot.jsp" />
	<jsp:include page="../base/js.jsp" />
</body>
<script>
	function delProject(id) {
		if (!confirm("项目删除后将无法恢复,所属信封也会随之删除,是否继续操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : "id=" + id,
			url : 'delProject.${defSuffix}',
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
