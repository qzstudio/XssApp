<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link href="${basePath }assets/css/page.css" rel="stylesheet">
<div class="page">
	<div id="kkpager" class="nowrap">
		<div>
			<span class="pageBtnWrap"> <a
				href="javascript:aheadPage()" 
				${(pager.currentPage<=1)?"class='disabled'":""}>上一页</a> <span
				class="curr">${pager.currentPage }/${pager.totalPages }页</span> <a
				href="javascript:nextPage()" 
				${(pager.currentPage>=pager.totalPages)?"class='disabled'":""}>下一页</a>
				页码 <input type="text" id="jumpPager" class="am-form-field am-field-valid"
				onkeydown="javascript:if(event.keyCode==13){toPage();}"
				value="${(pager.currentPage+2>=pager.totalPages)?pager.totalPages:pager.currentPage+2 }"
				style="width: 36px"
				>
				<input type="button" value="跳转" onclick="jumpPage()"
				style="cursor: pointer;" /></span>
				<input name="currentPage" id="currentPage" value="${pager.currentPage }" type="hidden">
			<script>
			function indexPage() {
		$("#currentPage").val('1');
		document.searchForm.submit();
	}
	function nextPage() {
		var curr = ${pager.currentPage};
		var total = ${pager.totalPages};
		if (curr >= total) {
			return false;
		}
		var next = ${pager.currentPage}+1;
		$("#currentPage").val(next);
		document.searchForm.submit();
	}
	function lastPage() {
		$("#currentPage").val('${pager.totalPages}');
		document.searchForm.submit();
	}
	function aheadPage() {
		var curr = ${pager.currentPage};
		if (curr <= 1) {
			return false;
		}
		var page = ${pager.currentPage}-1;
		$("#currentPage").val(page);
		document.searchForm.submit();
	}
	function jumpPage() {
		var jump = $("#jumpPager").val();
		var totalPages = ${pager.totalPages};
		if (jump > totalPages || jump < 1) {
			return false;
		}
		$("#currentPage").val(jump);
		document.searchForm.submit();
	}
			</script>
		</div>
		<div style="clear:both;"></div>
	</div>
</div>