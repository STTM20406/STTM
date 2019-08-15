<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
$(document).ready(function(){
	// 삭제버튼
	$("#deleteBtn").on("click",function(){
		if(confirm("정말 삭제할거에요?")== true){
			$("#frm").attr("action","/postDelete");
			$("#frm").attr("method","get");
			$("#frm").submit();
		}else{
			return false;
		}
	})
	
	// 수정하기 버튼
	$("#modifyBtn").on("click",function(){
		$("#frm").attr("action","/postModify");
		$("#frm").attr("method","get");
		$("#frm").submit();
	})
	
	// 댓글등록하기 버튼
	$("#replyBtn").on("click",function(){
		$("#frm").attr("action","/postView");
		$("#frm").attr("method","post");
		$("#frm").submit();
	})
	
	// 댓글삭제하기 버튼
	$(".replyTr #replyBUT").on("click",function(){
			console.log("replyTr click");
			console.log($(this));
			var replyNum = $(this).children().attr("name");
			console.log(replyNum);
			$("#replynum1").val(replyNum);
			
			$("#frm").attr("action","/replyDelete");
			$("#frm").attr("method","POST");
			$("#frm").submit();
			
		})

	
})
</script>


<section class="contents">
		<div>
			<label>제목</label>
			<label>${writeInfo.subject }</label>
			<label>댓글수 : ${replyCnt }</label>
			<label>좋아요 수 : ${replyCnt }</label>
			
			<button type="button">좋아요</button>
		
		</div>
		<div>
			<label>내용</label>
			<label>${writeInfo.content }</label>
		</div>
	<div>
	<form id="frm" action="/post" method="get">
		<input type="hidden" id="write_id" name="write_id" value="${writeInfo.write_id }"/>
		<input type="hidden" id="board_id" name="board_id" value="${writeInfo.board_id }"/>
		<input type="hidden" id="user_email" name="user_email" value="${USER_INFO.user_email }"/>
		<input type="hidden" id="replynum1" name="replynum1" value=""/>
		
			<label>댓글 목록</label><br>
			<table class="tb_style_01">
				<tbody>
					<tr>
						<th>번호</th>
						<th>내용</th>
						<th>작성자</th>
						<th>작성일</th>
					</tr>
					<c:forEach items="${replyList }" var="reply">
						<c:choose>
							<c:when test="${reply.user_email == USER_INFO.user_email}">
								<tr class="replyTr">
									<td class="replynum" style="display:none;">${reply.comm_id }</td>
									<td>${reply.rn }</td>
									<td>${reply.content }</td>
									<td>${reply.user_email }</td>
									<td><fmt:formatDate value="${reply.writedate }" pattern="yyyy-MM-dd"/></td>
									<td id="replyBUT"><button type="button" id="deleteReplyBtn" name="${reply.comm_id }">댓글삭제</button></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td>${reply.rn }</td>
									<td>${reply.content }</td>
									<td>${reply.user_email }</td>
									<td><fmt:formatDate value="${reply.writedate }" pattern="yyyy-MM-dd"/></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>
				<label>댓글 작성</label><br>
				<textarea rows="1" cols="60" name="r_content"></textarea>
				<button type="button" name="replyBtn" id="replyBtn"> 댓글등록 </button>
			
		</div>
		
		<button type="button" class="btn_style_04" name="modifyBtn" id="modifyBtn"> 수정 </button>
		<button type="button" class="btn_style_02" name="deleteBtn" id="deleteBtn"> 삭제 </button>

	</form>
</section>

