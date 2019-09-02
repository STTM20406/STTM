<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
$(document).ready(function(){
	// 삭제버튼
	$("#deleteBtn").on("click",function(){
		if(confirm("정말 삭제할거에요?")== true){
			$("#frm").attr("action","/userInquiryDelete");
			$("#frm").attr("method","get");
			$("#frm").submit();
		}else{
			return false;
		}
	})
	
	// 수정하기 버튼
	$("#modifyBtn").on("click",function(){
		$("#frm").attr("action","/userInquiryModify");
		$("#frm").attr("method","get");
		$("#frm").submit();
	})
})

</script>


<section class="contents">
		<div class="boardViewWrap">
			<div class="boardViewHd">
				<h2>${inquiryInfo.subject }</h2>
			</div>
			<div class="viewContent">
				<label>${inquiryInfo.inq_con }</label>
			</div>
		<div>
		<form id="frm">
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
			<div class="boardBtn">
				<button type="button" class="btn_style_04" name="modifyBtn" id="modifyBtn"> 수정 </button>
				<button type="button" class="btn_style_02" name="deleteBtn" id="deleteBtn"> 삭제 </button>
			</div>
		</form>
	</div>
</section>

