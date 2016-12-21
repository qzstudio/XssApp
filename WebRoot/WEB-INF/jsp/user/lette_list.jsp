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
								<th colspan="6">
									<div class="am-cf am-padding">
										<div class="am-fl am-cf">
											<strong class="am-text-primary am-text-lg">XssApp</strong> /
											<small>信封列表</small>
										</div>
									</div>

								</th>
							</tr>
						</thead>
						<thead>
							<tr class="am-disabled">
								<th></th>
								<th>项目</th>
								<th>来源</th>
								<th>IP</th>
								<th>时间</th>
								<th>管理</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${(empty pager)||(empty pager.pageData)}">
								<tr>
									<td colspan="6" style="text-align: center"><span
										class="am-avg-sm-4 am-thumbnails">暂无数据</span></td>
								</tr>
							</c:if>
							<c:forEach items="${pager.pageData }" var="letter"
								varStatus="index">
								<tr>
									<td><input name="id" type="checkbox" value="${letter.id }"
										id="myId" /></td>
									<td>${letter.project.title}</td>
									<td class="refUrl">${letter.refUrl }</td>
									<td>${letter.ip }</td>
									<td><fmt:formatDate value="${letter.updateTime }"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td ><a class="nowrap"
										href="${basePath }user/letterInfo.${defSuffix}?id=${letter.id}">查看</a>
										<a class="nowrap" href="#" onclick="delLetter(${letter.id})">删除</a>
								</tr>
							</c:forEach>
						</tbody>
						<thead>
							<tr class="am-disabled">
								<th colspan="3" class="nowrap"><input type="checkbox"
									id="allCheck" onchange="CheckAll()"><label
									for="allCheck"><small>全选</small></label> <label
									onclick="delLetters()" style="cursor:pointer "><small>删除选中</small></label></th>
								<th colspan="3"><jsp:include page="../base/page.jsp" /></th>
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
