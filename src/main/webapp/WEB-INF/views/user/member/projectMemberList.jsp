<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- basic Library --%>
<%@include file="/WEB-INF/views/common/baseLib.jsp"%>

<style>
	
	.userTr:hover{
		cursor: pointer;
	}
	
</style>

<script>
$(document).ready(function(){

		
	// 프로젝트 멤버 tr클릭시 레이어창 띄우기
	$('.prjMember').on("click", function(){
	        var $href = $(this).attr('href');
	        layer_popup($href);
	});

	// 요청 받은 친구 목록 클릭시
	$('.requestedFriendsList').on("click", function(){
	        var $href = $(this).attr('href');
	        layer_popup($href);
	});
	
});	


</script>

<section class="contents">

	<div class="tab_con">
	
		<div id="tab-1" class="tab-content current">
			
			<form id="prjMemView" action="/projectMemView" method="get">
				<input type="hidden" id="user_email" name="user_email">
			</form>
			
			<div>
				<table class="tb_style_01" id="prjMemTable">
					<colgroup>
						<col width="30%">
						<col width="30%">
						<col width="30%">
					</colgroup>
					<tbody>
						<tr>
						
							<th>사용자 이메일</th>
							<th>사용자 이름</th>
							<th></th>
		
							<c:forEach items="${projectMemList}" var="prjVo">
								<input type="hidden" value="${prjMemFriList}">
								<tr class="prjMemTr" data-user_email="${prjVo.user_email}">
									<td class="user_email" id="${prjVo.user_email}">
										<a href="#layer2" class="prjMember">${prjVo.user_email}</a><span id="log_${prjVo.user_email }" class="logout"> ●</span>
									</td>
									
									<td>${prjVo.user_nm}</td>
									
									<td>
										
										<c:forEach items="${prjMemFriList}" var="friVo">
												
											<c:choose>

												<c:when test="${prjVo.user_email eq friVo.user_email or prjVo.user_email eq friVo.frd_email}">
													
												</c:when>

<%-- 												<c:when test="${prjVo.user_email eq friVo.frd_email}"> --%>

<%-- 												</c:when> --%>
												
												<c:otherwise>
													<a href="/projectMemberList?frdRequEmail=${prjVo.user_email}" id="friendReqAtag" class="inp_style_01">친구요청</a>
												</c:otherwise>
												
											</c:choose>	
												
										</c:forEach>										
											
									</td>
									
								</tr>
								
							</c:forEach>
								
						</tr>
					</tbody>
				</table>
				
				 <div class="col-lg-12" id="ex1_Result2" ></div> 

			</div>
			
			<div class="pagination">
					<c:choose>
						<c:when test="${pageVo.page == 1 }">
							<a class="btn_first"></a>
						</c:when>
						<c:otherwise>
							<a href="${cp}/projectMemberList?page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
						
						</c:otherwise>
					</c:choose>
		
					<c:forEach begin="1" end="${paginationSize}" var="i">
						<c:choose>
							<c:when test="${pageVo.page == i}">
								<span>${i}</span>
							</c:when>
							<c:otherwise>
							<a href="${cp}/projectMemberList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
							</c:otherwise>
						</c:choose>
		
					</c:forEach>
		
					<c:choose>
						<c:when test="${pageVo.page == paginationSize}">
							<a href class="btn_last"></a>
						</c:when>
						
						<c:otherwise>
							<a href="${cp}/projectMemberList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
						</c:otherwise>
					</c:choose>
			
			</div>
			
	</div>
	
</div>

</section>