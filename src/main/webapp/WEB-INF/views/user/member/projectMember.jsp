<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

여기로 올까요?

<style>
	.userTr:hover{
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
	$(".userTr").on("click", function(){
		console.log("userTr click");
		//userId를 획득하는 방법
		//$(this).find(".userId").text();
		//$(this).data("userid");
		
		//사용자 아이디를 #userId 값으로 설정해주고
		var user_email = $(this).find(".user_email").text();
		$("#getMemInfo").val(user_email);
		
		//#frm 을 이용하여 submit();
		$("#showMemForm").submit();
	});
	// ------------------------------------------------------
});	
</script>

<section class="contents">

	<div id="tab-1" class="tab-content current">
		
		<!--  -->
		<form id="showMemForm" action="/admUserView" method="get">
			<input type="hidden" id="getMemInfo" name="getMemInfo" >
		</form>
		
		<div>
			<div class="searchBox">
				<div class="tb_sch_wr">
					<fieldset id="hd_sch">
					 	<form id="frmSearch" action="/admUserInfoSearch" method="get">
		<input type="hidden" id="inq_cate" name="inq_cate" value="INQ01"/>
							<input type="hidden" id="selectBoxText" name="selectBoxText" value="userEmail"/>
			                <legend>사이트 내 전체검색</legend>
<!-- 				                <select id="search" name="selectBoxText"> -->
				                <select id="search">
				                	<option value="userEmail">이메일</option>
				                	<option value="userNm">이름</option>
				                	<option value="userHp">전화번호</option>
				                </select>
			                <input type="text" name="keyword" id="keyword" maxlength="20" placeholder="검색어를 입력해주세요">
			                <button type="submit" id="sch_submit" value="검색">검색</button>
		                </form>
	            	</fieldset>
	           	</div>
	          	</div>
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
					
						<th>사용자 이메일</th>
						<th>사용자 이름</th>
						<th>사용자 휴대폰 번호</th>
						<th>회원 상태</th>
	
						<c:forEach items="${userList}" var="userVo">
						
							<tr class="userTr" data-user_email="${userVo.user_email }">
								<td class="user_email">${userVo.user_email}</td>
								<td>${userVo.user_nm}</td>
								<td>${userVo.user_hp}</td>
								<td>${userVo.user_st}</td>
							</tr>
							
						</c:forEach>
						
					</tr>
				</tbody>
			</table>
		</div>
		
		<a href="/admInsertUser" class="btn btn-default pull-right">사용자 등록</a>
	
		<div class="pagination">
				<c:choose>
					<c:when test="${pageVo.page == 1 }">
						<a href class="btn_first"></a>
					</c:when>
					<c:otherwise>
						<a href="${cp}/admUserList?page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
					
					</c:otherwise>
				</c:choose>
	
				<c:forEach begin="1" end="${paginationSize}" var="i">
					<c:choose>
						<c:when test="${pageVo.page == i}">
							<span>${i}</span>
						</c:when>
						<c:otherwise>
						<a href="${cp}/admUserList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
						</c:otherwise>
					</c:choose>
	
				</c:forEach>
	
				<c:choose>
					<c:when test="${pageVo.page == paginationSize}">
						<a href class="btn_last"></a>
					</c:when>
					
					<c:otherwise>
						<a href="${cp}/admUserList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
					</c:otherwise>
				</c:choose>
		
		</div>
	</div>

</section>