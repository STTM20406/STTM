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
	

	
	//댓글 등록하면 리스트 다시 가져오기
	function replyList(r_content, write_id){
		$.ajax({
			url:"/postViewAjax",
			method:"get",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			data: "r_content=" + r_content + "&write_id=" + write_id,
			success:function(data){
				
				var html = "";
				
				
				data.data.forEach(function(item, index){
					
					html += "<tr class='replyTr'>";
					html +=	"<td class='replynum' style='display:none;'>" + item.comm_id + "</td>";
					html +=	"<td>" + item.rn  + "</td>";
					html +=	"<td>" + item.content + "</td>";
					html +=	"<td>" + item.user_email + "</td>";
					html +=	"<td>" + item.writedateString + "</td>";
					html +=	"<td id='replyBUT'><button type='button' id='deleteReplyBtn' name='" + item.comm_id  + "'>댓글삭제</button></td>";
					html += "</tr>";
					
				});
				
				
				$("#inputAnswer").html(html);
			}
			
		});
		
	};
	
	//댓글 등록하면 댓글 수 증가
	function increaseReplyCntAjax(write_id){
		$.ajax({
			url:"/increaseReplyCntAjax",
			method:"get",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			data: "write_id=" + write_id,
			success:function(data){
				console.log("댓글수 : " + data.data)
				var html = "";
				html += "<label>댓글수 : " +  data.data + "</label>";
				$("#replyCntAjax").html(html);
				
			}
		});
	};
	
	// 댓글등록하기 버튼
	$("#replyBtn").on("click", function(){
// 		$("#frm").attr("action","/postView");
// 		$("#frm").attr("method","post");
// 		$("#frm").submit();

		var write_id = $("#write_id").val();
		var r_content = $("#r_content").val();
		console.log("댓글 등록" + write_id + r_content);
		replyList(r_content, write_id);
		increaseReplyCntAjax(write_id);
		
	});
	
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

	$(".contents").on("click", "#likeBtnPush", function(){
		
		var write_id = $("#write_id").val();
		var board_id = $("#board_id").val();
		
		$(this).attr("value", "좋아요 안누름");
		$(this).attr("id", "likeBtnNotPush");
		
		
		DownLikeAjax(write_id,board_id);
		
	})
	
	function DownLikeAjax(write_id, board_id){
		$.ajax({
			url:"/downLikeAjax",
			method:"post",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			data: "write_id=" + write_id + "&board_id=" + board_id,
			success:function(data){
				var text = data.data;
				console.log(text);
				var html = "";
				html += "<label>좋아요 수 : " + data.data + "</label>";
				$("#likeCnt label").html(html);
			}
			
		});
		
	}
	
	
	$(".contents").on("click", "#likeBtnNotPush", function(){
		var write_id = $("#write_id").val();
		var board_id = $("#board_id").val();

		$(this).attr("value", "좋아요 누름");
		$(this).attr("id", "likeBtnPush");

		AddlikeAjax(write_id, board_id);
		
	})
	
	function AddlikeAjax(write_id, board_id){
		$.ajax({
			url:"/addLikeAjax",
			method:"post",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			data: "write_id=" + write_id + "&board_id=" + board_id,
			success:function(data){
				console.log(data.data);
				var text = data.data;
				console.log(text);
				var html = "";
				html += "<label>좋아요 수 : " + data.data + "</label>";
				$("#likeCnt label").html(html);
			}
			
		});
		
	}
	
	
	
	
	
})
</script>


<section class="contents">
		<div>
			<label>제목</label>
			<label>${writeInfo.subject }</label>
			<div id="replyCntAjax">
				<label>댓글수 : ${replyCnt }</label>
			</div>
			<div id="likeCnt">
				<label>좋아요 수 : ${writeInfo.like_cnt }</label>
				<c:if test="${likeCheck == 1}">
					<input type="button" id="likeBtnPush" value="좋아요누름" name="${likeCheck}">
				</c:if>
				<c:if test="${likeCheck == 0}">
					<input type="button" id="likeBtnNotPush" value="좋아요안누름" name="${likeCheck}">
				</c:if>
			</div>
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
				<tbody id="inputAnswer">
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
				<textarea rows="1" cols="60" id="r_content" name="r_content"></textarea>
				<input type="button" name="replyBtn" id="replyBtn" value="댓글등록">
			
		</div>
		
		<button type="button" class="btn_style_04" name="modifyBtn" id="modifyBtn"> 수정 </button>
		<button type="button" class="btn_style_02" name="deleteBtn" id="deleteBtn"> 삭제 </button>

	</form>
</section>

