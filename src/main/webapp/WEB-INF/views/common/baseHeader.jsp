<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html lang="ko">
<head>
<meta charset="utf-8">
<!-- <meta http-equiv="X-UA-Compatible" content="IE=Edge"> -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>STTM</title>

<%-- basic Library --%>
<%@include file="/WEB-INF/views/common/baseLib.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#selectProject").on("change", function(){
			var projectID = $(this).children("option:selected").attr("id");
			$("#projectId").val(projectID);
			$("#projectFrm").submit();
		});
	});
</script>

</head>
<body>

<div id="wrap">
      <!-- top header -->
      
	<header id="header">
		<div class="headerWrap">
			<div class="hdTop">
				<div class="logo">
					<h2><a href="/login">project</a></h2>
				</div>
				
				<form id="projectFrm" action="/work/list" method="post">
					<input type="hidden" name="prj_id" id="projectId" value="">
				</form>
				
				<div class="project_list_option">
					<select id="selectProject" name="selectProject">
						<option>Select Project</option>
						<c:forEach items="${projectList}" var="projectList">
						<option id="${projectList.prj_id}">${projectList.prj_nm}</option>
						</c:forEach>
					</select>
				</div>
			
				<nav id="gnb">
					<ul>
						<li><a href="/notification" id="aTagCountReset">NOTIFY<span id="spanCountReset" class="light">${USER_INFO.count_notify }</span></a></li>
						<li><a href="/overview/analysis">WORK LIST</a></li>
						<li><a href="/project/list">PROJECT LIST</a></li>
						<li><a href="/projectMember">MEMBER</a></li>
						<li><a href="/friendChatList">CHATTING</a></li>
						<li><a href="/userInquiry">INQUERY</a></li>
						<c:forEach items="${userBoardListY }" var = "board">
							<li class="board_id"><a href="/community?board_id=${board.board_id }" id="${board.board_id }">${board.name }</a></li>
						</c:forEach>
					</ul>
				</nav>
			</div>
		
			<div class="user_info_wrap">
				<ul>
					<li><a href="#" class="icon_set"><span class="color_style01">${USER_INFO.user_nm}</span> welcome</a></li>
					<li>
						<p><a href="/setUserPass">계정설정</a></p>
						<p><a href="/logout">로그아웃</a></p>
					</li>
				</ul>
			</div>
			
			<!-- header search box start -->
			<div class="hd_sch_wr">
				<fieldset id="hd_sch">
					<legend>사이트 내 전체검색</legend>
					<form id="hsearchFrm" action="/project/headerSearch" method="get">
						<select id="headerSearch" name="headerSearch">
							<option value="1">업무리스트</option>
							<option value="2">업무명</option>
							<option value="3">프로젝트 멤버명</option>
						</select> <input type="text" name="headerSearchText" id="headerSearchText" maxlength="20" placeholder="프로젝트 검색">
						<button type="submit" id="hsch_submit" value="검색">검색</button>
					</form>
				</fieldset>
			</div>
			<!-- header search box end -->
		</div>
	</header>
