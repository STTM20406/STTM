<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
	.chatBot{width:490px; height: 490px; background : #25F40D; position: fixed; left : 240px; bottom:0; }
</style>

<script>
	$(document).ready(function(){
		$('.chatBot').hide();
		$('#chatBotBtn').on('click',function(){
			$('.chatBot').show(250);
		})
		
	});
</script>
<!-- side bar -->
<div class="sidebar">
	<div class="logo">
		<h2><a href="#">project</a></h2>
		<div class="auth">
			<p class="auth_txt"><span>제한</span></p>
			<ul class="auth_list">
				<li>전체 액세스</li>
				<li>제한 액세스</li>
				<li>통제 액세스</li>
			</ul>
		</div>
	</div>
	<div class="project_list_option">
		<select>
			<option>병원 사이트 리뉴얼</option>
			<option>창업지원플랫폼 구축</option>
		</select>
	</div>
	<nav id="gnb">
		<ul>
			<li><a href="#">NOTIFY</a></li>
			<li><a href="/overview/analysis">WORK LIST</a></li>
			<li><a href="/project/list">PROJECT LIST</a></li>
			<li><a href="/projectMemberList">MEMBER</a></li>
			<li><a href="/friendChatList">CHATTING</a></li>
			<li><a href="/userInquiry">INQUERY</a></li>
			<c:forEach items="${userBoardListY }" var = "board">
				<li class="board_id"><a href="/community?board_id=${board.board_id }" id="${board.board_id }">${board.name }</a></li>
			</c:forEach>
			<li><a href="#" id="chatBotBtn">ChatBot</a></li>
		</ul>
	</nav>
</div>

<div class="chatBot">
	
	
</div>






