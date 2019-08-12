<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
#fs{
	margin : 0 auto;
	text-allign : center;
}
#subject{ 
	width:500px;  
	height:80px;     
	resize:none;
}

#special{ 
	width:500px;  
	height:60px;     
	resize:none;
}
</style>

<script>
	$(document).ready(function(){
		
		
	});
	function insert(){
		var user_email = [];
		$("input[name=attender]:checked").each(function(){
			user_email.push($(this).val());
		});
		
		$('#user_email').val(user_email);
		
		var subject = $('#subject').val();
		var special = $('#special').val();
		$('#insertSubject').val(subject);
		$('#insertSpecial').val(special);
		
		$("#insertForm").submit();
		
	}
	

</script>


<fieldSet id="fs">
	<form id="insertForm" action="/insertConference" method="post">
		<input type="hidden" id="user_email" name="user_email">
		<input type="hidden" id="insertSubject" name="insertSubject">
		<input type="hidden" id="insertSpecial" name="insertSpecial">
	</form>
	
	<label>♬ 참석자   : </label>
	<c:forEach items="${userList}" var="uList">
		<input type="checkbox" id="check" value="${uList.user_email}" name="attender">${uList.user_nm}
	</c:forEach>
		
	<div>
		<label>♬♩♪ 회의내용</label><br>
		<textarea id="subject"></textarea>
	</div>
	<br>
	<div>
		<label>♬♩♪ 특이사항</label><br>
		<textarea id="special"></textarea>
	</div> 
	<input type="button" id="writer" onclick="insert()" value="작성완료">&nbsp;&nbsp;&nbsp;
	<a type="button" href="/conferenceList">뒤로</a>

    
</fieldSet>