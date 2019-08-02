<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
	.userTr:hover{
		cursor: pointer;
	}
</style>

<script>
$(document).ready(function(){
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
});	

function admUpdateUserView() {
	$("#btnMemUpdateView").on("click",function(){
		$("#memViewForm").submit();
		alert("회원 정보 수정 화면 입니다");			
	});
}
</script>

<section class="contents">

	<div id="tab-1" class="tab-content current">
		
		<form action="/admUpdateUser" method="post" id="memViewForm">	
			<table class="tb_style_01">
				<tr>
					<th>회원정보</th>
					<th></th>
				</tr>
				<tr>
					<td>이메일</td>
					<td>${userVo.user_email}</td>
				</tr>
				<tr>
					<td>이름</td>
					<td>${userVo.user_nm}</td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td>${userVo.user_hp}</td>
				</tr>
				<tr>
					<td>상태</td>
					<td>${userVo.user_st}</td>
				</tr>
			</table>
		</form>	
			
		<input type="button" id="btnMemUpdateView" onclick="admUpdateUserView()" value="수정">
		
	</div>

</section>