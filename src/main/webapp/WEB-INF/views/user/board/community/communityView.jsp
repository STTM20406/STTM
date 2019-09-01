<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	});
	
	window.onkeyup = function(e) {
	    var code = e.keyCode || e.which;
	    if (code == 13) {
	        $('#replyBtn').click();
	    }
	};
	
	// 수정하기 버튼
	$("#modifyBtn").on("click",function(){
		$("#frm").attr("action","/postModify");
		$("#frm").attr("method","get");
		$("#frm").submit();
	})
	
	
	//채팅방 이름 범위넘으면 출력
	$('#r_content').keyup(function(){
        if ($(this).val().length > $(this).attr('maxlength')) {
            alert('입력할 수 있는 제목 범위를 넘었습니다');
            $(this).val($(this).val().substr(0, $(this).attr('maxlength')));
        }
	});
	
	//댓글 등록하면 리스트 다시 가져오기
	function replyList(r_content, write_id){
		$.ajax({
			url:"/postViewAjax",
			method:"get",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
			data: "r_content=" + r_content + "&write_id=" + write_id,
			success:function(data){
				
				var html = "";
				html += "<tr>";
				html += "<th>번호</th>";
				html += "<th>내용</th>";
				html += "<th>작성자</th>";
				html += "<th>작성일</th>";
				html += "<th></th>";
				html += "</tr>";
				
				data.data.forEach(function(item, index){
					
					html += "<tr class='replyTr'>";
					html +=	"<td class='replynum' style='display:none;'>" + item.comm_id + "</td>";
					html +=	"<td>" + item.rn  + "</td>";
					html +=	"<td>" + item.content + "</td>";
					html +=	"<td>" + item.user_email + "</td>";
					html +=	"<td>" + item.writedateString + "</td>";
					if(item.user_email == data.user_email){
						html += "<td id='replyBUT'><input type='button' class='inp_style_01' id='deleteReplyBtn' name='" + item.comm_id + "' value='댓글삭제'></td>";
					
					}
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

		if($("#r_content").val().length == 0){
			alert("등록할 댓글을 입력하세요");
			return false;
		}

		var write_id = $("#write_id").val();
		var r_content = $("#r_content").val();
		console.log("댓글 등록" + write_id + r_content);
		replyList(r_content, write_id);
		
		setTimeout(function() {
			increaseReplyCntAjax(write_id);
		}, 600);
		
		$("#r_content").val('');
		$("#r_content").focus();
		
	});
	
	// 댓글삭제하기 버튼
	$("#inputAnswer").on("click", "#replyBUT", function(){
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
				html += "<label>좋아요 " + data.data + "</label>";
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
				html += "<label>좋아요 " + data.data + "</label>";
				$("#likeCnt label").html(html);
			}
			
		});
		
	}
	
	
	
	
	
})
</script>

<section class="contents">
		<div class="boardViewWrap">
			<div class="boardViewHd">
				<h2>${writeInfo.subject }</h2>
				<div class="viewInfo">
					<div id="replyCntAjax">
						<label><span>댓글</span> ${replyCnt }</label>
					</div>
					<div id="likeCnt">
						<label><span>좋아요</span> ${writeInfo.like_cnt }</label>
						<c:if test="${likeCheck == 1}">
							<input type="button" id="likeBtnPush" value="좋아요누름" name="${likeCheck}">
						</c:if>
						<c:if test="${likeCheck == 0}">
							<input type="button" id="likeBtnNotPush" value="좋아요안누름" name="${likeCheck}">
						</c:if>
					</div>
				</div>
			</div>
			<div class="viewContent">${writeInfo.content }</div>
		<div class="viewComment">
			<form action="#" id="frm">
				<input type="hidden" id="write_id" name="write_id" value="${writeInfo.write_id }"/>
				<input type="hidden" id="board_id" name="board_id" value="${writeInfo.board_id }"/>
				<input type="hidden" id="user_email" name="user_email" value="${USER_INFO.user_email }"/>
				<input type="hidden" id="replynum1" name="replynum1" value=""/>
					<label>댓글 목록</label><br>
					<table class="tb_style_04">
						<tbody id="inputAnswer">
							<tr>
								<th>번호</th>
								<th>내용</th>
								<th>작성자</th>
								<th>작성일</th>
								<th></th>
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
											<c:choose>
												<c:when test="${reply.user_email == USER_INFO.user_email}">
													<td id="replyBUT"><input type="button"  class="inp_style_01" id="deleteReplyBtn" name="${reply.comm_id }" value="댓글삭제"></td>
												</c:when>
											</c:choose>
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
				</form>
				<div class="boardBtn">
					<input type="text" id="r_content" name="r_content" maxlength="70">
					<input type="button" class="inp_style_01" name="replyBtn" id="replyBtn" value="댓글등록">
				</div>
			</div>
			<div class="boardBtn">
			<c:choose>
				<c:when test="${writeInfo.user_email == USER_INFO.user_email}">
					<button type="button" class="btn_style_04" name="modifyBtn" id="modifyBtn"> 수정 </button>
					<button type="button" class="btn_style_02" name="deleteBtn" id="deleteBtn"> 삭제 </button>
				</c:when>
			</c:choose>	
			</div>
	</div>
</section>

