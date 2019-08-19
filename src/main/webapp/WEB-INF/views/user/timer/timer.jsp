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
	
	// ------------------------------------------------------
	//사용자 tr 태그 이벤트 등록
	$(".workListTr").on("click", function(){
		
		var user_email = $(this).find(".user_email").text();
		$("#getMemInfo").val(user_email);
		
		$("#showMemForm").submit();
	});

	// ------------------------------------------------------
});	

function wrokComplete() {
	
	$("#setTimeWork").submit();
	alert("업무가 완료 상태로 설정 되었습니다.")
}
</script>

<section class="contents">


	<div id="tab-1" class="tab-content current">
		
		<!--  -->
<!-- 		<form id="showMemForm" action="/admUserView" method="get"> -->
<!-- 			<input type="hidden" id="getMemInfo" name="getMemInfo" > -->
<!-- 		</form> -->
		
		<form id="setTimeWork" action="/setWorkCompleate" method="post">
			<div>
				<table class="tb_style_01">
					<colgroup>
						<col width="10%">
						<col width="20%">
						<col width="10%">
						<col width="20%">
						<col width="20%">
						<col width="10%">
						<col width="10%">
					</colgroup>
					<tbody>
						<tr>
						
							<th>프로젝트 아이디</th>
							<th>프로젝트 이름</th>
							<th>업무리스트 아이디</th>
							<th>업무리스트 이름</th>
							<th>사용자 이름</th>
							<th>업무 완료 여부</th>
							<th>업무 완료 체크</th>
		
							<c:forEach items="${workList}" var="workListVo">
								
								<tr class="workListTr" data-wrk_lst_id="${workListVo.wrk_lst_id }">
									
									<td class="workListId">${workListVo.prj_id}</td>
									<td>${workListVo.prj_nm}</td>
									<td >${workListVo.wrk_lst_id}</td>
									<td>${workListVo.wrk_lst_nm}</td>
									<td>${workListVo.user_nm}</td>
									<td>
<%-- 									${workListVo.wrk_cmp_fl} --%>
										<input type="text" id="wrk_cmp_fl" name="wrk_cmp_fl" value="${workListVo.wrk_cmp_fl}">
									</td>
									<td>
										<input type="button" onclick="wrokComplete()" value="완료" class="inp_style_01">
									</td>
								</tr>
								
							</c:forEach>
							
						</tr>
					</tbody>
				</table>
			</div>
		</form>
		
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