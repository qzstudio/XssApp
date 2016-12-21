<%@page import="com.xss.web.util.DateUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
<jsp:include page="base/head.jsp" />
</head>
<body>
	<div class="am-cf am-padding">
		<div class="am-
fl am-cf">
			<strong class="am-text-primary am-text-lg">首页</strong> / <small>index</small>
		</div>
	</div>
	<ul
		class="am-avg-sm-1 am-avg-md-4 am-margin am-padding am-text-center admin-content-list ">
		<li><a href="#" class="am-text-success"><span
				class="am-icon-btn am-icon-file-text"></span><br />会员数目<br />${userCount }</a></li>
		<li><a href="#" class="am-text-warning"><span
				class="am-icon-btn am-icon-briefcase"></span><br />信封数目<br />${letterCount }</a></li>
		<li><a href="#" class="am-text-danger"><span
				class="am-icon-btn am-icon-recycle"></span><br />项目数目<br />${projectCount }</a></li>
		<li><a href="#" class="am-text-secondary"><span
				class="am-icon-btn am-icon-user-md"></span><br />模版数目<br />${moduleCount }</a></li>
	</ul>
	<div class="am-u-sm-12">
		<table class="am-table am-table-bordered">
			<thead>
				<tr class="am-disabled">
					<td colspan="2" style="text-align: center">网站基本信息</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>用户名:<span class="t4">
							${curr_login_admin.userName }</span></td>
					<td>IP：<span class="t4"> <%=request.getLocalAddr()%></span></td>
				</tr>
				<tr>
					<td>身份过期：<font class="t4">30 分钟</font></td>
					<td>现在时间：<font class="t4"><%=DateUtils.dateToString(new Date(),
					DateUtils.DATETIME_PATTERN)%></font></td>
				</tr>
				<tr>
					<td>物理路径： <font class="t4"><%=request.getServletContext().getRealPath("")%></font></td>
					<td>上线时间：<font class="t4"><%=DateUtils.dateToString((Date) request.getSession()
					.getAttribute("loginTime"), DateUtils.DATETIME_PATTERN)%></font></td>
				</tr>
				<tr>
					<td>服务器域名： <font class="t4">${basePath }</font>
					</td>
					<td>脚本解释引擎：<font class="t4"><%=response.getHeader("X-Powered-By")%></font>
					</td>
				</tr>
				<tr>
					<td>服务器软件的名称： <font class="t4"><%=response.getHeader("Server")%></font>
					</td>
					<td>浏览器版本：<font class="t4"><%=request.getHeader("User-Agent")%></font>
					</td>
				</tr>
				<tr>
					<td>FSO文本读写： <b>√</b>
					</td>
					<td>数据库使用： <b>√</b>
					</td>
				</tr>
				<tr>
					<td>Jmail组件支持： <b>√</b>
					</td>
					<td>CDONTS组件支持： <b>√</b>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<br />
	<br />
	<jsp:include page="base/js.jsp" />
</body>
<style>
.t4 {
	font: 12px 宋体;
	color: #800000;
}
</style>
</html>
