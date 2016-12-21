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
					<strong class="am-text-primary am-text-lg">权限管理</strong> / <small>后台菜单编辑</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-4'}">
					后台菜单编辑<span class="am-icon-chevron-down am-fr"></span>
				</div>
				<div id="collapse-panel-4" class="am-panel-bd am-collapse am-in">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8"></div>
						<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">

							<div class="am-form-group">
								<label for="user-name" class="am-u-sm-3 am-form-label"><small>菜单标题</small></label>
								<div class="am-u-sm-9">
									<input name="id" type="hidden" value="${menu.id }" /> <input
										type="text" id="user-name" placeholder="菜单标题"
										value="${menu.title }" name="title"> <small>请输入菜单标题</small>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-email" class="am-u-sm-3 am-form-label"><small>菜单URL</small></label>
								<div class="am-u-sm-9">
									<input type="email" id="user-email" placeholder="菜单URL"
										value="${menu.url }" name="url"> <small>请输入菜单URL(不带后缀)</small>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-email" class="am-u-sm-3 am-form-label"><small>菜单排序</small></label>
								<div class="am-u-sm-9">
									<input type="email" id="user-email" placeholder="菜单排序"
										value="${menu.seq }" name="seq"> <small>请输入菜单排序</small>
								</div>
							</div>
							<div class="am-form-group">
								<label for="user-phone" class="am-u-sm-3 am-form-label"><small>菜单类别</small></label>
								<div class="am-u-sm-9">
									<select name="type">
										<option value="">请选择角色</option>
										<option value="0"
											<c:if test="${menu.type==0 }">selected</c:if>>一级菜单</option>
										<option value="1"
											<c:if test="${menu.type==1 }">selected</c:if>>二级菜单</option>
									</select> <small>请选择菜单类别</small>
								</div>
							</div>

							<div class="am-form-group">
								<label for="user-phone" class="am-u-sm-3 am-form-label"><small>上级菜单</small></label>
								<div class="am-u-sm-9">
									<select name="menus.id">
										<option value="">请选择上级菜单</option>
										<c:forEach items="${menuList }" var="parentMenu">
											<option value="${parentMenu.id }"
												<c:if test="${menu.menus.id==parentMenu.id }">selected</c:if>>${parentMenu.title }</option>
										</c:forEach>
									</select> <small>请选择菜单类别</small>
								</div>
							</div>
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
					url : 'menuSave.${defSuffix}',
					timeout : 60000,
					success : function(json) {
						alert(json.msg);
						if (json.code == 0) {
							location.href = '${basePath }admin/menuList.${defSuffix}';
						}

					},
					error : function() {
						alert("系统繁忙");
					}

				});
	}
</script>
</html>
