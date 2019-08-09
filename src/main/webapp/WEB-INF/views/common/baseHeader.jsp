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

<!-- 메모 dropdown -->
<style>
.dropbtn {
  background-color: #3498DB;
  color: white;
  padding: 16px;
  font-size: 16px;
  border: none;
  cursor: pointer;
}

.dropbtn:hover, .dropbtn:focus {
  background-color: #2980B9;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  overflow: auto;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown a:hover {background-color: #ddd;}

.show {display: block;}
</style>

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
	$(".socketAlram").hide();
function connectNotify(){
	console.log("웹소켓알림시작하거라~~~");
	socket = new SockJS("/echo.do");
	
	socket.onopen = function() {
		console.log('Info : connection opened');

	};

	socket.onmessage = function(event) {
		console.log("ReceiveMessage: ", event.data + "\n");
		var $socketAlert = $('#socketAlert p');
		$socketAlert.text(event.data);
		$(".socketAlram").fadeIn(300);
		$(".socketAlram").animate({right:"0px"}, 500);
		setTimeout(function(){
			$(".socketAlram").fadeOut(300);
			$(".socketAlram").animate({right:"-350px"}, 500);
			
		},3000);

	};

	socket.onclose = function(event) {
		console.log('info: connection closed');
	};

	socket.onerror = function(err) {
		console.log('error: ', err);
	};
	
}

// 새로추가한 메모 dropdown
function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

</script>

<style>
	.socketAlram {background: red;
	color: white;
    position: fixed;
    right: -350px;
    top: 160px;
    width: 350px;
    padding: 25px;
    z-index:999}

</style>

</head>
<body>
<div id="socketAlert" class="socketAlram" role="alert">
	<p></p>
</div>
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

        	<div id="tnb" class="dropdown">
        		<ul>
        		 <button onclick="myFunction()" class="dropbtn">메모<span class="caret"></span></button>
        		 	
        		 	<div id="myDropdown" class="dropdown-content">
		        		<c:forEach items="${projectList }" var="pro" >
		        			<a href="#"><span class="color_style01">${pro.prj_nm }</span></a>
		        		</c:forEach>
        		 	</div>
        		 	
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