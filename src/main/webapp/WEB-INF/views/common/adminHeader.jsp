<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>STTM ADMIN</title>

<%-- basic Library --%>
<%@include file="/WEB-INF/views/common/adminLib.jsp"%>

<script>
	$(document).ready(function(){
		$(".user_set_list").hide();
		$(".icon_set").on("click", function(){
			$(".user_set_list").fadeIn();
		});
		$(".user_set_list").on("mouseleave", function(){
			$(".user_set_list").fadeOut();
		});
		
	});
</script>


</head>
<body>
	<div id="wrap">
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
					<li><a href="/boardAdd">BOARDMANAGER</a></li>
					<li><a href="#">NOTIFY</a></li>
					<li><a href="#">WORK LIST</a></li>
					<li><a href="/project/list">PROJECT LIST</a></li>
					<li><a href="#">MEMBER</a></li>
					<li><a href="/friendChatList">CHATTING</a></li>
					<li><a href="/admInquiry">INQUERY</a></li>
					<c:forEach items="${admBoardListY }" var = "board">
						<li><a href="#">${board.name }</a></li>
					</c:forEach>
				</ul>
			</nav>
		</div>

		<!-- top header -->
		<header id="header">

			<!-- header search box start -->
			<div class="hd_sch_wr">
	            <fieldset id="hd_sch">
	                <legend>사이트 내 전체검색</legend>
	                <form name="" action="" onsubmit="">
		                <select>
		                	<option>검색옵션</option>
		                	<option>업무명</option>
		                </select>
		                <input type="text" name="" id="" maxlength="20" placeholder="검색어를 입력해주세요">
		                <button type="submit" id="sch_submit" value="검색">검색</button>
	                </form>
	            </fieldset>
        	</div>
        	<!-- header search box end -->

        	<div id="tnb">
        		<ul>
        			<li><a href="#"><span class="color_style02">타이머</span></a></li>
        			<li><a href="#"><span class="color_style01">화상회의</span></a></li>
        			<li><a href="#"><span class="color_style01">채팅</span>리스트</a></li>
        			<li><a href="#"><span class="color_style01">${USER_INFO.user_nm}</span>님 환영합니다</a></li>
        			<li>
        				<a href="#" class="icon_set">설정</a>
        				<div class="user_set_list">
        					<dl>
        						<dt></dt>
        						<dd><a href="">계정설정</a></dd>
        						<dd><a href="/logout">로그아웃</a></dd>
        					</dl>
        				</div>
        			</li>
        		</ul>
        	</div>
		</header>