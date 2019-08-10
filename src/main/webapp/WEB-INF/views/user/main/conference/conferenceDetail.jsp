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

<fieldSet id="fs">
	    <label>♬♩♪ 회의록 번호 : ${minutesVo.mnu_id}</label>
		<label>♬ 참석자 : </label>
	    <c:forEach items="${minutes_memList}" var="List">
	    	<label>${List.user_nm} </label>
	    </c:forEach>
	    <br>
		<label>♩ 작성자 : ${minutesVo.user_nm}</label>
		<fmt:formatDate value="${minutesVo.write_date}" var="date" pattern="yyyy-MM-dd HH:mm" />
		<label>♪ 작성일자 : ${date}</label>
		<br><br>
	<div>
		<label>♬♩♪ 회의내용</label><br>
		<textarea id="subject" readonly="readonly" name="subject">${minutesVo.subject}</textarea>
	</div>
	<br>
	<div>
		<label>♬♩♪ 특이사항</label><br>
		<textarea id="special" readonly="readonly" name="special">${minutesVo.special}</textarea>
	</div>
	
	<!-- 내가 작성한 글일 떄만 수정 삭제 버튼 기릿 -->
	<c:choose>
		<c:when test="${user_nm eq minutesVo.user_nm}">
			<a href="/upMinutes?mnu_id=${minutesVo.mnu_id}">수정</a>&nbsp;&nbsp;&nbsp;<a href="/delMinutes?mnu_id=${minutesVo.mnu_id}">삭제</a>
		</c:when>
	</c:choose>
	
</fieldSet>