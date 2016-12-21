<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
	<div class="am-offcanvas-bar admin-offcanvas-bar">
		<ul class="am-list admin-sidebar-list">
			<c:forEach items="${adminMenus }" var="menu" varStatus="index">
				<li class="admin-parent"><a class="am-cf"
					href="${basePath}${(menu.url eq '#')?menu.childMenus[0].url:menu.url }.${defSuffix }"
					target="index"><span
						class="am-icon-file"></span> ${menu.title }<span
						class="am-icon-angle-right am-fr am-margin-right"></span></a> <c:if
						test="${!empty menu.childMenus}">
						<ul class="am-list am-collapse admin-sidebar-sub am-in"
							id="collapse-nav${index.index }">
							<c:forEach items="${menu.childMenus }" var="chileMenu">
								<li><a href="${basePath}${chileMenu.url }.${defSuffix }"
									class="am-cf" target="index"><span class="am-icon-check"></span>${chileMenu.title }</a></li>
							</c:forEach>
						</ul>
					</c:if></li>
			</c:forEach>
		</ul>

		<div class="am-panel am-panel-default admin-sidebar-panel">
			<div class="am-panel-bd">
				<p>
					<span class="am-icon-bookmark"></span> 技术支持
				</p>
				<p>代码描绘人生：QQ644556636</p>
			</div>
		</div>

	</div>
</div>