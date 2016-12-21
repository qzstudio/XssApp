<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<header class="am-topbar admin-header">
	<div class="am-topbar-brand">
		<strong>Scrum Group Xss</strong> <small>运营管理平台</small>
	</div>
	<div class="am-collapse am-topbar-collapse" id="topbar-collapse">
		<ul
			class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
			<li  class="navLi"><a href="${basePath }index.${defSuffix}">首页</span></a></li>
			<c:if test="${empty curr_login_user }">
				<li class="navLi"><a href="${basePath }login.${defSuffix}">登录</span></a></li>
				<li class="navLi"><a href="${basePath }reg.${defSuffix}">注册</span></a></li>
			</c:if>
			<c:if test="${!empty curr_login_user }">
				<li class="navLi"><a href="${basePath }user/index.${defSuffix}">项目列表</span></a></li>
				<li class="navLi"><a href="${basePath }user/loadUserModule.${defSuffix}">模块列表</span></a></li>
				<li class="navLi"><a href="${basePath }user/loadLetters.${defSuffix}">信封列表</span></a></li>
				<li class="navLi"><a href="${basePath }user/loadEmails.${defSuffix}">系统邮箱列表</span></a></li>
				<li class="am-dropdown navLi" data-am-dropdown><a
					class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;"> <span
						class="am-icon-users"></span> ${curr_login_user.userName } <span
						class="am-icon-caret-down"></span>
				</a>
					<ul class="am-dropdown-content">
						<li ><a href="${basePath }user/setting.${defSuffix}" class="navNext"><span
								class="am-icon-cog"></span> 基本设置</a></li>
						<li ><a href="${basePath }user/modifyPwd.${defSuffix}" class="navNext"><span
								class="am-icon-power-off"> 修改密码</span></a></li>
						<li><a href="${basePath }user/loginOut.${defSuffix}" class="navNext"><span
								class="am-icon-power-off"></span> 注销登录</a></li>
					</ul></li>
			</c:if> 
		</ul>
		
	</div>
</header>