<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
	.workListTr:hover{
		cursor: pointer;
	}
</style>

<script>
$(document).ready(function(){
	
	$("#search").on("change",function(){
		console.log($("#search").val());
		var searchValue = $("#search").val();
		$("#selectBoxText").val(searchValue);
	})
	
	$("#sch_submit").on("click",function(){
		$("#frmSearch").submit();
	})
	
	// ------------------------------------------------------
	//사용자 tr 태그 이벤트 등록
	$(".workListTr").on("click", function(){
		
		var user_email = $(this).find(".user_email").text();
		$("#getMemInfo").val(user_email);
		
		$("#showMemForm").submit();
	});

	// ------------------------------------------------------
});	
</script>

<section class="contents">

	<div id="tab-1" class="tab-content current">
		
		<!--  -->
<!-- 		<form id="showMemForm" action="/admUserView" method="get"> -->
<!-- 			<input type="hidden" id="getMemInfo" name="getMemInfo" > -->
<!-- 		</form> -->
		
		<input type="hidden" id="user_email" name="user_email" value="#{user_email}">
		
		<div>
			<table class="tb_style_01">
				<colgroup>
					<col width="10%">
					<col width="40%">
					<col width="30%">
					<col width="10%">
					<col width="10%">
				</colgroup>
				<tbody>
					<tr>
					
						<th>업무리스트 아이디</th>
						<th>프로젝트 아이디</th>
	
						<c:forEach items="${workList}" var="workListVo">
							<tr class="workListTr" data-wrk_lst_id="${workListVo.wrk_lst_id }">
								<td class="workListId">${workListVo.wrk_lst_id}</td>
								<td>${workListVo.prj_id}</td>
							</tr>
							
						</c:forEach>
						
					</tr>
				</tbody>
			</table>
		</div>
		
		<a href="/admInsertUser" class="btn btn-default pull-right">
			<input type="button" class="inp_style_01" value="사용자 등록">
		</a>
		
		<div class="pagination">
				<c:choose>
					<c:when test="${pageVo.page == 1 }">
						<a href class="btn_first"></a>
					</c:when>
					<c:otherwise>
						<a href="${cp}/work/timerWorkList?page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
					
					</c:otherwise>
				</c:choose>
	
				<c:forEach begin="1" end="${paginationSize}" var="i">
					<c:choose>
						<c:when test="${pageVo.page == i}">
							<span>${i}</span>
						</c:when>
						<c:otherwise>
						<a href="${cp}/work/timerWorkList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
						</c:otherwise>
					</c:choose>
	
				</c:forEach>
	
				<c:choose>
					<c:when test="${pageVo.page == paginationSize}">
						<a href class="btn_last"></a>
					</c:when>
					
					<c:otherwise>
						<a href="${cp}/work/timerWorkList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
					</c:otherwise>
				</c:choose>
		
		</div>
	</div>
	
</section>