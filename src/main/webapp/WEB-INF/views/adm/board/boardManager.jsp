<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
$(document).ready(function(){
	
	$("#boardAddBtn").on("click",function(){
		$("#frm").submit();
	})
	
	
	$(".boardTr").find("button").on("click",function(){
		var a = $(this).closest(".boardTr").find(".board_id").children("#board_id").val();
		$("#board_id").val(a);
		var b = $(this).closest(".boardTr").find(".use_fl").children("#yn").val();
		$("#use_fl").val(b);
		var c = $(this).closest(".boardTr").find(".boardNM").children("#boardNM").val();
		$("#boardNM").val(c);
	
		$("#frm").attr("action","/boardModify");
		$("#frm").attr("method","post")		
		$("#frm").submit();
	})
})

</script>


<div>
<form id="frm" action="/boardAdd" method="post">

	<table class="tb_style_01">
		<tbody>
			<tr>
				<th>게시판 이름</th>
				<th>게시판 사용여부</th>
			</tr>
			<tr>
				<td>
					<input type="text" name="name" id="name" value=""/>
				</td>
				<td>
					<select name="use_fl" id="yn">
		                	<option value="Y">사용</option>
		                	<option value="N">미사용</option>
	                </select>
				</td>
				<td>
					<button type="button" id="boardAddBtn" class="btn_style_01">생성</button>
				</td>
			</tr>
		</tbody>
	</table>
	
	<input type="hidden" name="board_id02" id="board_id" value="">
	<input type="hidden" name="use_fl02" id="use_fl" value="">
	<input type="hidden" name="name02" id="boardNM" value="">
	
	<table class="tb_style_01">
		<tbody>
			<tr>
				<th>게시판 번호</th>
				<th>게시판 이름</th>
				<th>게시판 사용여부</th>
				
			</tr>
			<c:forEach items="${boardList }" var="board">
				<tr class="boardTr">
					<td class="board_id">
						${board.board_id }
						<input type="hidden" name="board_id" id="board_id" value="${board.board_id}"/>
					</td>
					<td class="boardNM">
						<input type="text" name="boardNM" id="boardNM" value="${board.name}"/>
					</td>
					<c:choose>
						<c:when test="${board.use_fl == 'Y' }">
							<td class="use_fl">
								<select id="yn">
				                	<option value="Y" selected="selected">사용</option>
				                	<option value="N">미사용</option>
				                </select>
							</td>
						</c:when>
						
						<c:otherwise>
							<td class="use_fl">
								<select id="yn">
				                	<option value="Y">사용</option>
				                	<option value="N" selected="selected">미사용</option>
				                </select>
							</td>
						</c:otherwise>
					</c:choose>
					<td>
						<button type="button" id="boardEdit" class="btn_style_04">수정</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>
</div>