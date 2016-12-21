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
					<strong class="am-text-primary am-text-lg">权限管理</strong> / <small>后台菜单管理</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-base'}">
					后台菜单管理<span class="am-icon-chevron-down am-fr"
						style="display: none"></span><a href="${basePath }admin/menuEdit.${defSuffix}"
						class="am-btn am-btn-default am-round am-fr am-btn-xs"
						style="float: right">添加菜单</a>
				</div>
				<div id="collapse-panel-base" class="am-panel-bd am-collapse am-in">
					<div class="am-panel am-panel-default  am-btn-xs">
						<c:forEach items="${menuList }" var="menu" varStatus="index">
							<div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-${index.index }'}">
								${menu.title}
								<div style="float: right">
									<a href="#" onclick="delDate(${menu.id })"
										class="am-btn am-btn-primary am-round am-fr am-btn-xs">删除</a>
									<a
										href="${basePath }admin/menuEdit.${defSuffix}?id=${menu.id }"
										class="am-btn am-btn-primary am-round am-fr am-btn-xs">编辑</a>

								</div>
								
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
										<li><span class="am-avg-sm-4 am-thumbnails">${childMenu.title}
												<div style="float: right">
													<a href="#" onclick="delDate(${childMenu.id })"
														class="am-btn am-btn-default am-round am-fr am-btn-xs">删除</a>
													<a
														href="${basePath }admin/menuEdit.${defSuffix}?id=${menu.id }"
														class="am-btn am-btn-default am-round am-fr am-btn-xs">编辑</a>

												</div>
										</span></li>
									</c:forEach>
								</ul>
							</div>
						</c:forEach>
					</div>
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
			url : 'menuDel.${defSuffix}',
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
</html>
