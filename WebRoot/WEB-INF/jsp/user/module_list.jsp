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
								<th colspan="5">
									<div class="am-cf am-padding">
										<div class="am-fl am-cf">
											<strong class="am-text-primary am-text-lg">XssApp</strong> /
											<small>模块列表</small>
										</div>
									</div>
								</th>
							</tr>
						</thead>
						<thead>
							<tr class="am-disabled ">
								<th>编号</th>
								<th>模块名称</th>
								<th>描述</th>
								<th>更新时间</th>
								<th>管理</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty userModules}">
								<tr>
									<td colspan="5" style="text-align: center"><span
										class="am-avg-sm-4 am-thumbnails">暂无数据</span></td>
								</tr>
							</c:if>
							<c:forEach items="${userModules }" var="module">
								<tr>
									<td>${module.id }</td>
									<td>${module.title}</td>
									<td>${module.remark }</td>
									<td><fmt:formatDate value="${module.updateTime }"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td ><a class="nowrap" 
										href="${basePath }user/moduleEdit.${defSuffix}?id=${module.id}">编辑</a>
										<a class="nowrap"  href="#" onclick="delModule(${module.id})">删除</a></td>
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
	function delModule(id) {
		if (!confirm("模块删除后将无法恢复,所属项目参数也会随之删除,是否继续操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : "id=" + id,
			url : 'delModule.${defSuffix}',
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
