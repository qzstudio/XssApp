<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
	<div class="am-panel am-panel-default">
		<div class="am-panel-hd am-cf"
			data-am-collapse="{target: '#collapse-panel-1'}">
			<a href="${basePath }user/index.${defSuffix}">我的项目</a><span class="am-icon-chevron-down am-fr"></span> <a
				href="${basePath }user/projectAdd.${defSuffix}">
				<button type="submit" name=""
					class="am-btn am-btn-default am-round am-fr am-btn-xs">
					 创建
				</button>
			</a>
		</div>
		<div class="am-panel-bd am-collapse am-in" id="collapse-panel-1">
			<ul class="am-list admin-content-file" style="text-align: left">
				<c:if test="${empty projects }">
					<li  class="moduleLi"><small>暂无数据</small></li>
				</c:if>
				<c:forEach items="${projects }" var="project">
					<li class="moduleLi"><a href="${basePath }user/projectEdit.${defSuffix}?id=${project.id}"><small>${project.title }</small></a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="am-panel am-panel-default">
		<div class="am-panel-hd am-cf"
			data-am-collapse="{target: '#collapse-panel-2'}">
			<a href="${basePath }user/loadUserModule.${defSuffix}">我的模块</a><span class="am-icon-chevron-down am-fr"></span><a
				href="${basePath }user/moduleAdd.${defSuffix}">
				<button type="submit" name=""
					class="am-btn am-btn-default am-round am-fr am-btn-xs">
					 创建
				</button>
			</a>
		</div>
		<div class="am-panel-bd am-collapse am-in" id="collapse-panel-2">
			<ul class="am-list admin-content-file" style="text-align: left">
				<c:if test="${empty userModules }">
					<li  class="moduleLi"><small>暂无数据</small></li>
				</c:if>
				<c:forEach items="${userModules }" var="module">
					<li  class="moduleLi"><a
						href="${basePath }user/moduleEdit.${defSuffix}?id=${module.id}"><small>${module.title }</small></a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="am-panel am-panel-default">
		<div class="am-panel-hd am-cf am-collapsed"
			data-am-collapse="{target: '#collapse-panel-3'}">
			<a href="#">公共模块</a><span class="pa-left"></span><span
				class="am-icon-chevron-down am-fr"></span>
		</div>
		<div class="am-panel-bd am-collapse" id="collapse-panel-3">
			<ul class="am-list admin-content-file" style="text-align: left">
				<c:if test="${empty sysModules }">
					<li  class="moduleLi"><small>暂无数据</small></li>
				</c:if>
				<c:forEach items="${sysModules }" var="module">
				<c:if test="${(empty module.user) ||module.user.id!=curr_login_user.id }">
				<li  class="moduleLi"><a
						href="${basePath }user/moduleEdit.${defSuffix}?id=${module.id}"><small>${module.title }</small></a></li>
				</c:if>
					
				</c:forEach>
			</ul>
		</div>
		<style>
.list {
	display: compact;
}
</style>
	</div>
</div>
<style>
.moduleLi{
    line-height: 0.6;
}
</style>