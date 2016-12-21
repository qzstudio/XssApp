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
							<tr class="am-disabled">
								<th colspan="3">
									<div class="am-cf am-padding">
										<div class="am-fl am-cf">
											<strong class="am-text-primary am-text-lg">XssApp</strong> /
											<small>系统邮箱列表</small>
										</div>
									</div>
								</th>
							</tr>
						</thead>
						<thead>
							<tr class="am-disabled">
								<th>编号</th>
								<th>邮箱服务器</th>
								<th>邮箱账号</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty emails}">
								<tr>
									<td colspan="3" style="text-align: center"><span
										class="am-avg-sm-4 am-thumbnails">暂无数据</span></td>
								</tr>
							</c:if>
							<c:forEach items="${emails }" var="email" varStatus="index">
								<tr>
									<td>${email.id }</td>
									<td>${email.smtp}</td>
									<td>${email.email }</td>
								</tr>
							</c:forEach>
						</tbody>
						<thead>
							<tr class="am-disabled ">
								<th colspan="3"><small style="color: red">注：请将系统邮箱添加到邮件白名单</small>
							</tr>
						</thead>
					</table>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="../base/foot.jsp" />
	<jsp:include page="../base/js.jsp" />
</body>
<script>
	function CheckAll() {
		if ($("#allCheck").is(":checked")) {
			$("[name=id]:checkbox").each(function() {
				$(this).prop("checked", true);
			});
			return;
		}
		$("[name=id]:checkbox").each(function() {
			$(this).prop("checked", false);
		});
	}
	function delLetter(id) {
		if (!confirm("信封删除后将无法恢复,所属参数也会随之删除,是否继续操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : "id=" + id,
			url : 'delLetter.${defSuffix}',
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
	function delLetters() {
		if (!confirm("信封删除后将无法恢复,所属参数也会随之删除,是否继续操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#searchForm").serialize(),
			url : 'delLetter.${defSuffix}',
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
