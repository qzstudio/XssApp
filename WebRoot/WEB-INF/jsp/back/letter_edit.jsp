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
					<strong class="am-text-primary am-text-lg">内容管理</strong> / <small>信封查看</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-4'}">
					信封查看<span class="am-icon-chevron-down am-fr"></span>
				</div>
				<div id="collapse-panel-4" class="am-panel-bd am-collapse am-in">
					<div class="am-g">
						<fieldset>
							<legend>所属项目：${letter.project.title }</legend>
							<legend>所属模版：${letter.project.module.title }</legend>
							<div class="am-form-group">
								<label for="doc-vld-ta-2"><small>Referer：</small><small
									style="color: red">${letter.refUrl }</small></label>
							</div>
							<div class="am-form-group">
								<label for="doc-vld-ta-2"><small>IP地址：</small><small
									style="color: red">${letter.ip }  [${ip_address.country } ${ip_address.area } ${ip_address.region } ${ip_address.city } ${ip_address.isp }]</small></label>
							</div>
							<c:forEach items="${paras }" var="para">
								<div class="am-form-group">
									<label for="doc-vld-ta-2"><small>${para.paraName }：</small></label>
									<textarea id="doc-vld-ta-2" name="remark">${para.paraValue }</textarea>
								</div>
							</c:forEach>
							<button type="button" onclick="history.back()"
								class="am-btn am-btn-warning am-round am-fr">
								<i class="am-icon-spinner am-icon-spin"></i> 返回
							</button>
						</fieldset>
					</div>
				</div>
	</form>
</body>
<jsp:include page="base/js.jsp" />
</html>
