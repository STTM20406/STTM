<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
$(document).ready(function(){
	// 답변버튼
	$("#replyBtn").on("click",function(){
		$("#frm").submit();
	})
})

</script>


<section class="contents">
		<div>
			<label>제목</label>
			<label>${inquiryInfo.subject }</label>
		</div>
		<div>
			<label>내용</label>
			<label>${inquiryInfo.inq_con }</label>
		</div>
		<div>
	<form id="frm" action="/admInquiryView" method="post">
	<input type="hidden" id="inq_id" name="inq_id" value="${inquiryInfo.inq_id }"/>
			<label>답변</label>
			<c:choose>
				<c:when test="${inquiryInfo.ans_st == 'N'}">
					<label>답변이 없습니다.</label>
				</c:when>
				<c:otherwise>
					<label>${inquiryInfo.ans_con }</label>
				</c:otherwise>
			</c:choose>
			
		</div>
		
		<textarea rows="1" cols="60" name="iq_content"></textarea>
		<button type="button" name="replyBtn" id="replyBtn"> 답변등록 </button>

	</form>
</section>