<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>STTM</title>

<%-- basic Library --%>
<%@include file="/WEB-INF/views/common/baseLib.jsp"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.0/sockjs.min.js"></script>

<script>
var socket = null;

	$(document).ready(function(){
		connectNotify();
	
		$(".user_set_list").hide();
		$(".icon_set").on("click", function(){
			$(".user_set_list").fadeIn();
		});
		$(".user_set_list").on("mouseleave", function(){
			$(".user_set_list").fadeOut();
		});
		$(".board_id").on("click",function(){
			var c = $(this).children().attr("id");
			$(".board_id").val(c);	

		})
		
		
	});
function connectNotify(){
	console.log("웹소켓알림시작하거라~~~");
	socket = new SockJS("/echo.do");
	
	socket.onopen = function() {
		console.log('Info : connection opened');

	};

	socket.onmessage = function(event) {
		console.log("ReceiveMessage: ", event.data + "\n");
		var $socketAlert = $('div#socketAlert');
		$socketAlert.text(event.data);
		$socketAlert.css('display','block');
		setTimeout(function(){
			$socketAlert.css('display','none');
			
		},3000);

	};

	socket.onclose = function(event) {
		console.log('info: connection closed');
	};

	socket.onerror = function(err) {
		console.log('error: ', err);
	};
	
}


</script>


</head>
<body>
<div id="socketAlert" role="alert" style="display:none; background:skyblue;"></div>
	<div id="wrap">
		
		<%@include file="/WEB-INF/views/common/baseLeft.jsp"%>
	
		<!-- top header -->
		<header id="header">

			<!-- header search box start -->
			<div class="hd_sch_wr">
	            <fieldset id="hd_sch">
	                <legend>사이트 내 전체검색</legend>
	                <form name="" action="" onsubmit="">
		                <select>
		                	<option>검색옵션</option>
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
        						<dd><a href="/setUserPass">계정설정</a></dd>
        						<dd><a href="/setUserProfile">프로필설정</a></dd>
        						<dd><a href="/logout">로그아웃</a></dd>
        					</dl>
        				</div>
        			</li>
        		</ul>
        	</div>
		</header>