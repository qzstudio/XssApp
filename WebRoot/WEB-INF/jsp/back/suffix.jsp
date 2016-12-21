<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.xss.web.util.DateUtils"%>
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
					<strong class="am-text-primary am-text-lg">系统管理</strong> / <small>网站后缀设置</small>
				</div>
			</div>
			<hr>
			<div class="am-panel am-panel-default">
				<div class="am-panel-hd am-cf"
					data-am-collapse="{target: '#collapse-panel-4'}">
					网站后缀设置<span class="am-icon-chevron-down am-fr"></span>
				</div>
				<div id="collapse-panel-4"
					class="am-table am-table-bordered am-table-radius am-table-striped">
					<div class="am-g">
						<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8"></div>
						<table class="am-table am-table-bordered">
							<tbody>
								<tr>
									<td  colspan="5" height="25"
										align="center"><b>网站后缀管理</b></td>
								</tr>
								<tr>
									<c:forEach items="${suffixList }" var="suffix"
										varStatus="index">
										<td><input type="checkbox" name="suffix[]"
											id="checkBox${index.index }"
											<c:if test="${suffix.status>0 }">checked="checked"</c:if>
											value="${suffix.id }" /> ${suffix.suffix } <c:if
												test="${suffix.status==2 }">
												<span style="color: red">默认后缀</span>
											</c:if> <c:if test="${suffix.status==1 }">
												<a href="#" onclick="defaule(${suffix.id})"
													style="color: blue">设为默认</a>
											</c:if></td>
										<c:if test="${(index.index+1)%5==0 &&index.index!=0 }">
								</tr>
								</c:if>
								<c:if test="${(index.index+1)%5==0 &&index.index!=0}">
									<tr>
								</c:if>
								</c:forEach>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-primary"
								onclick="update()">保存修改</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
<jsp:include page="base/js.jsp" />
<script type="text/javascript">
	function update(){
		if(confirm("确认进行此操作?")){
			$.ajax({   
			    type:"POST",  
			    dataType : 'json',  
			    data:$("#dataform").serialize(),
			    url:'${basePath}admin/updateSuffix.${defSuffix}',  
			    timeout:60000,  
			    success:function(json){ 
			    alert(json.msg); 
			    	if(json.code==0){
			    		location.reload(true);
			    	}
			    } ,
			    error:function(){
			    	alert("系统繁忙");
			    }
			});  
		}
	}
	function defaule(id){
		if(confirm("确认进行此操作?")){
				$.ajax({   
			    type:"POST",  
			    dataType : 'json',  
			    data:"id="+id,
			    url:'${basePath}admin/defaultSuffix.${defSuffix}',  
			    timeout:60000,  
			    success:function(json){ 
			    alert(json.msg); 
			    	if(json.code==0){
			    		location.reload(true);
			    	}
			    } ,
			    error:function(){
			    	alert("系统繁忙");
			    }
			});  
		}
	}
</script>
</html>
