<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
	.chatBot{width:490px; height: 490px; background : #f5f5f5; position: fixed; left : 240px; bottom:0; }
	.chatContent {overflow-y:scroll; height:400px}
 	.chatContent:after {content:""; clear:both; display:block; zoom:1}
	.chatContent .bot {display:block; width:100%}
	.chatContent .me {display:block; width:100%; text-align:right}
	
	.chatInput {position:absolute; bottom:0; left:0; width:100%; height:100px}
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
	<div class="chatContent">
		<dl class="bot">
			<dt>챗봇잉</dt>
			<dd>뭘도와드려유?</dd>
		</dl>
		<dl class="me">
			<dt>나미꼬</dt>
			<dd>여러가지...뒤돌아요</dd>
		</dl>
		<dl class="bot">
			<dt>챗봇잉</dt>
			<dd>뭘도와드려유?</dd>
		</dl>
		<dl class="me">
			<dt>나미꼬</dt>
			<dd>여러가지...뒤돌아요</dd>
		</dl>
		<dl class="bot">
			<dt>챗봇잉</dt>
			<dd>뭘도와드려유?</dd>
		</dl>
		<dl class="me">
			<dt>나미꼬</dt>
			<dd>여러가지...뒤돌아요</dd>
		</dl>
		<dl class="bot">
			<dt>챗봇잉</dt>
			<dd>뭘도와드려유?</dd>
		</dl>
		<dl class="me">
			<dt>나미꼬</dt>
			<dd>여러가지...뒤돌아요</dd>
		</dl>
		<dl class="bot">
			<dt>챗봇잉</dt>
			<dd>뭘도와드려유?</dd>
		</dl>
		<dl class="me">
			<dt>나미꼬</dt>
			<dd>여러가지...뒤돌아요</dd>
		</dl>
		<dl class="bot">
			<dt>챗봇잉</dt>
			<dd>뭘도와드려유?</dd>
		</dl>
		<dl class="me">
			<dt>나미꼬</dt>
			<dd>여러가지...뒤돌아요</dd>
		</dl>
		<dl class="bot">
			<dt>챗봇잉</dt>
			<dd>뭘도와드려유?</dd>
		</dl>
		<dl class="me">
			<dt>나미꼬</dt>
			<dd>여러가지...뒤돌아요</dd>
		</dl>
		
	</div>
	<div class="chatInput">
		<textarea name="" id="" placeholder="입력해 주세요."></textarea>
		<input type="button" id="" name="" value="모내기">
	</div>
</div>






