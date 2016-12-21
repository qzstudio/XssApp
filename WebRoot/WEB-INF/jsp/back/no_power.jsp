<%@page import="com.xss.web.util.DateUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<jsp:include page="base/head.jsp" />
</head>
<body>
<p style="height: 60px;"></p>
	<div style="margin:0px auto">
		<div class="am-container">
			<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
				<div class="am-panel am-panel-default">
					<div class="am-panel-hd">系统提示</div>
					<div class="am-panel-bd">无权操作，请联系超级管理员</div>
				</div>
				<hr>
			</div>
		</div>
	</div>
	<jsp:include page="base/js.jsp" />
</body>
<style>
.t4 {
	font: 12px 宋体;
	color: #800000;
}
</style>
</html>
