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
		<input type="hidden" name="roleId" value="${role.id }">
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">权限管理</strong> / <small>角色权限分配</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default  am-btn-xs">
				<c:forEach items="${menuList }" var="menu" varStatus="index">
					<div class="am-panel-hd am-cf">
						<input type="checkbox" name="ids[]" id="checkBox${index.index }"
							value="${menu.id }"
							<c:if test="${!empty roleMap[menu.id] }">checked</c:if> />${menu.title}<span
							class="am-icon-chevron-down am-fr"
							data-am-collapse="{target: '#collapse-panel-${index.index }'}"></span>
					</div>
					<div id="collapse-panel-${index.index }"
						class="am-panel-bd am-collapse am-in">
						<ul
							class="am-list am-list-static am-list-border am-list-striped am-list-xs">
							<c:if test="${ empty menu.menuses }">
								<li>暂无子菜单</li>
							</c:if>
							<c:forEach items="${menu.menuses }" var="childMenu"
								varStatus="childIndex">
								<li><input type="checkbox" name="ids[]"
									id="checkBox${childIndex.index }" value="${childMenu.id }"
									<c:if test="${!empty roleMap[childMenu.id] }">checked</c:if> />${childMenu.title}</li>
							</c:forEach>
						</ul>
					</div>
				</c:forEach>
				<div class="am-form-group">
					<div class="am-u-sm-9 am-u-sm-push-3">
						<button type="button" class="am-btn am-btn-primary"
							onclick="saveFormData()">保存修改</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
<style>
.am-panel-hd {
	padding: .6rem 1rem;
	border-bottom: 1px solid transparent;
}

address, blockquote, dl, fieldset, figure, hr, ol, p, pre, ul {
	margin: 0 0 1rem;
}

.am-panel-bd {
	padding: .65rem;
}

body, pre {
	line-height: 1;
}
</style>
<jsp:include page="base/js.jsp" />
<script>
	function saveFormData() {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : 'savePermis.${defSuffix}',
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
