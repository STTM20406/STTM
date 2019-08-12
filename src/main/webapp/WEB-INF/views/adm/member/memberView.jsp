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

});

function admUpdateUserView() {
	$("#admUpdate").submit();
}
</script>

<section class="contents">

	<div id="tab-1" class="tab-content current">
		
		
		<form id="admUpdate" action="/admUpdateUser" method="get" >	
			
<!-- 	<input type="hidden" id="admUpdate" name="admUpdate"> -->
		<input type="hidden" id="admUpdate" name="admUpdate" value="${userVo.user_email}">

			<table class="tb_style_01">
				<tr>
					<th>회원정보</th>
					<th></th>
				</tr>
				<tr>
					<td>이메일</td>
					<td id="user_email">${userVo.user_email}</td>
				</tr>
				<tr>
					<td>이름</td>
					<td id="user_nm">${userVo.user_nm}</td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td id="user_hp">${userVo.user_hp}</td>
				</tr>
				<tr>
					<td>상태</td>
					<td id="user_st">${userVo.user_st}</td>
				</tr>
			</table>
		</form>	
			
		<input type="button" id="btnMemUpdateView" onclick="admUpdateUserView()" value="수정">
		
	</div>

</section>