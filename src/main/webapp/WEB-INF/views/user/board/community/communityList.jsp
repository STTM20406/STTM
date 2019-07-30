<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
.boardTr:hover{
	cursor : pointer;
}
</style>

<script>
	$(document).ready(function(){
		$('#addBtn').click(function(){
			$("#frm").submit();	
		})
		
		$(".boardTr").on("click",function(){
			console.log("boardTr click");
			var boardNum = $(this).find(".boardNum").text();
			$("#write_id").val(boardNum);
			$("#frm").attr("action","/postView");
			$("#frm").attr("method","get");
			$("#frm").submit();
		})
		
	})
	
</script>


<section class="contents">

<h2>커뮤니티게시판</h2>
<form id="frm" action="/postAdd" method="get">
	<input type="text" name="boardnum" id="boardnum" value="${board_id }">
	<input type="hidden" id="write_id" name="write_id" value=""/>
	<table class="tb_style_01">
		<tbody>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>댓글</th>
				<th>좋아요</th>
				<th>조회수</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
			<c:forEach items="${boardList }" var="board">
				<tr class="boardTr">
					<td class="boardNum">${board.write_id }</td>
					<td>${board.subject }</td>
					<td>댓글수 들어갈거야</td>
					<td>${board.like_cnt }</td>
					<td>${board.view_cnt }</td>
					<td>${board.user_email }</td>
					<td><fmt:formatDate value="${board.writedate }" pattern="yyyy-MM-dd"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<button id="addBtn" type="button" class="btn_style_01">게시글 작성</button>

</form>
</section>