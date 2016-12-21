<%@page import="com.xss.web.util.DateUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<!doctype html>
<html class="no-js">
<head>
<jsp:include page="base/head.jsp" />
</head>
<body>
	<form class="am-form am-form-horizontal" method="post" id="searchForm"
		name="searchForm">
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">用户管理</strong> / <small>邀请码管理</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-base'}">
					邀请码管理<span class="am-icon-chevron-down am-fr" style="display: none"></span>
					<a href="${basePath }admin/inviteImport.${defSuffix}"
						class="am-btn am-btn-default am-round am-fr am-btn-xs"
						style="float: right">邀请码生成</a>
				</div>
				<div id="collapse-panel-base" class="am-panel-bd am-collapse am-in">
					<ul class="am-list am-list-static am-list-border">
						<li><span class="nowrap">
								<div class="am-panel am-panel-default" style="width: auto">
									<div class="am-panel-bd">
										邀请码:<input name="inviteCode"
											class="am-form-input  am-radius" type="email"
											style="width: auto;display: -webkit-inline-box"
											value="${inviteCode }" /> &nbsp;&nbsp;使用状态:<select
											class="am-form-input  am-radius"
											style="width: auto;display: -webkit-inline-box" name="status">
											<option value=""> 请选择使用状态 </option>
											<option value="1"
												<c:if test="${(!empty status)&&(status==1)}">selected</c:if>>未使用</option>
											<option value="0"
												<c:if test="${(!empty status)&&(status==0)}">selected</c:if>>已使用</option>
										</select> &nbsp;&nbsp;注册用户：<input name="user.userName"
											class="am-form-input am-radius" type="email"
											style="width:
										auto;display: -webkit-inline-box"
											value="${user_userName }" /> <a href="javascript:indexPage()"
											class="am-btn am-btn-default am-fr ">查询</a>
									</div>
								</div>
						</span></li>
					</ul>
					<table class="am-table am-table-bordered">
						<thead>
							<tr>
								<th>ID</th>
								<th>邀请码</th>
								<th>使用状态</th>
								<th>注册用户</th>
								<th>更新时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<c:if test="${empty pager.pageData }"><tr><td  colspan="6" style="text-align:center ">暂无数据</td></tr></c:if>
							<c:forEach items="${pager.pageData }" var="invite">
								<tr>
									<td><input name="id" type="checkbox" value="${invite.id }"
										id="myId" />${invite.id }</td>
									<td>${invite.inviteCode}</td>
									<td><c:if test="${invite.status==1 }">未使用</c:if>
										<c:if test="${invite.status==0 }">已使用</c:if></td>
									<td>${invite.user.userName }</td>
									<td>${invite.updateTime }</td>
									<td>
										<a href="#" onclick="delDate(${invite.id })">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
						<thead>
							<tr class="am-disabled">
							<th colspan="3" class="nowrap"><input type="checkbox"
									id="allCheck" onchange="CheckAll()"><label
									for="allCheck"><small>全选</small></label> <label
									onclick="delInvites()" style="cursor:pointer "><small>删除选中</small></label></th>
								<th colspan="3"><jsp:include page="../base/page.jsp" /></th>
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
	function delInvites() {
		if (!confirm("数据删除后将无法恢复,是否继续操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#searchForm").serialize(),
			url : 'inviteDel.${defSuffix}',
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
	function delDate(id) {
		if (!confirm("数据删除后将无法恢复,是否继续操作?")) {
			return;
		}
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : 'id=' + id,
			url : 'inviteDel.${defSuffix}',
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
