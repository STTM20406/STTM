<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
$(document).ready(function(){
	// 답변버튼
// 	$("#replyBtn").on("click",function(){
// 		$("#frm").submit();
// 	})
	$("#toList").on("click", function() {
		window.location = "/admInquiry";
	});	

	$(".contents").on("click", "#replyBtn",function(){
		var inq_id = $(this).siblings("#inq_id").val();
		var iq_content = $(this).siblings("#iq_content").val();
		
		if(!iq_content) {
			alert("내용을 입력해주세요.");
			return;
		}
		console.log(inq_id);
		console.log(iq_content);
		
		inqReply(inq_id,iq_content);
	})
	
	function inqReply(inq_id,iq_content){
		
		console.log(iq_content);
		$.ajax({
				url:"/admInquiryView1",
				method:"post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data : "inq_id="+inq_id+"&iq_content="+iq_content,
				success:function(data){
					console.log(data);
					var html = "";
					html += '<input type="hidden" id="inq_id" name="inq_id" value="${inquiryInfo.inq_id }" value="'+ inq_id+'">';
					html +="<label>답변</label> : ";
					html +=data.data.inquiryInfo.ans_con;
					html += '<br>' + '<input type="text" id="iq_content" name="iq_content"> ' + 
					'<button type="button" name="replyBtn" id="replyBtn"> 답변등록 </button>';
					$(".divSubject").html(html);
					
					console.log("소켓연결인지 확인",socket);
					if(socket){
						// websocket에 보내기!!
						var socketMsg = "notify," + data.data.inquiryInfo.user_email + "," + data.data.userId.user_email;
						console.log("메세지이이이이이이이이이",socketMsg);
						socket.send(socketMsg);
					}
				}
			})
		}
	
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
		<div class="divSubject">
<!-- 	<form id="frm" action="/admInquiryView" method="post"> -->
			<input type="hidden" id="inq_id" name="inq_id" value="${inquiryInfo.inq_id }">
			<label>답변</label> : 
			<c:choose>
				<c:when test="${inquiryInfo.ans_st == 'N'}">
					<label>답변이 없습니다.</label>
				</c:when>
				<c:otherwise>
					<label>${inquiryInfo.ans_con }</label>
				</c:otherwise>
			</c:choose>
			
		<br>
		<input type="text" id="iq_content" name="iq_content">
		<button type="button" name="replyBtn" id="replyBtn"> 답변등록 </button>
<!-- 	</form> -->
		</div>
		<button type="button" id="toList"> 목록 </button>
</section>