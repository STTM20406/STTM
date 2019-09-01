<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
$(document).ready(function(){
	$('#modify').on('click',function(){
		if($('#subject').val()==''){
			alert("수정 하실 사항을 입력하여 주십시오.");
		} else{
			$('#frm').submit();
		}
		
	});
});

</script>


<fieldSet id="fs">
	<form id="frm" action="/upMinutes" method="post">
	<input type="hidden" name="mnu_id" value="${minutesVo.mnu_id}">
		<label>♬♩♪ 회의록 번호 : ${minutesVo.mnu_id}</label> 
		<label>♬ 참석자 :</label>
		<c:forEach items="${minutes_memList}" var="List">
			<label>${List.user_nm} </label>
		</c:forEach>
		
		<br>
		<label>♩ 작성자 : ${minutesVo.user_nm}</label>
		<fmt:formatDate value="${minutesVo.write_date}" var="date" pattern="yyyy-MM-dd HH:mm" />
		<label>♪ 작성일자 : ${date}</label> <br>
		<br>
		<div>
			<label>♬♩♪ 회의내용</label><br> <input id="subject" type="text"	name="subject" value="${minutesVo.subject}">
		</div>
		<br>
		<div>
			<label>♬♩♪ 특이사항</label><br> <input id="special" type="text" name="special" value="${minutesVo.special}">
		</div>
	</form>

	<button id="modify" type="button">수정완료!</button>
	
</fieldSet>