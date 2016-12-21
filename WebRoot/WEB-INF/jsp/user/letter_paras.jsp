<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<html>
<head lang="en">
<jsp:include page="../base/head.jsp" />
</head>
<body style="text-align:center;">
	<div style="margin:0px auto">
		<jsp:include page="../base/nav.jsp" />
		<div class="am-container">
			<jsp:include page="../base/left.jsp" />
			<div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
				<div class="am-g">
					<div class="am-u-md-12">
						<div class="am-panel am-panel-default">
							<div class="am-panel-hd">信封详情</div>
							<div class="am-panel-bd">
								<form action="" class="am-form" id="dataform"
									data-am-validator="" novalidate="novalidate">
									<fieldset>
									<legend style="font-size: 1.4rem;text-align: left">所属项目：${letter.project.title }</legend>
										<legend  style="font-size: 1.4rem;text-align: left">所属模版：${letter.project.module.title }</legend>
										<div class="am-form-group">
											<label for="doc-vld-ta-2"><small>Referer：</small><small style="color: red">${letter.refUrl }</small></label>
										</div>
										<div class="am-form-group">
											<label for="doc-vld-ta-2"><small>IP地址：</small><small style="color: red">${letter.ip } [${ip_address.country } ${ip_address.area } ${ip_address.region } ${ip_address.city } ${ip_address.isp }]</small></label>
										</div>
										<c:forEach items="${paras }" var="para">
											<div class="am-form-group">
												<label for="doc-vld-ta-2"><small>${para.paraName }：</small></label>
												<textarea id="doc-vld-ta-2" name="remark">${para.paraValue }</textarea>
											</div>
										</c:forEach>
										
										<legend  style="font-size: 1.4rem;text-align: left">更新时间：${letter.updateTime }</legend>
										<button type="button" onclick="history.back()"
											class="am-btn am-btn-warning am-round am-fr">
											<i class="am-icon-spinner am-icon-spin"></i> 返回
										</button>
									</fieldset>
								</form>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../base/foot.jsp" />
	</div>
	<jsp:include page="../base/js.jsp" />
</body>
<style>
.am-form-group {
	text-align: left;
}
</style>
</html>
