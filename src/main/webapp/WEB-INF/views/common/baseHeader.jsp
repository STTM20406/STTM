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
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.0/sockjs.min.js"></script>

<!-- 메모 dropdown -->
<style>
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
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
}

.show {
	display: block;
}
</style>

<script>
var socket = null;

$(document).ready(function(){
	connectNotify();

		// 내가 한 일
		$("").on("click",function(){
			$("#memoView").animate({right:'0'}, 500);
		})
		
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
	
	$("").on("click",function(){
		$("#memoView").animate({right:'0'}, 500);
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
  	$(".dropdown-content").fadeIn(300);
	$(".dropdown-content").on("mouseleave", function(){
		$(this).fadeOut(300);
	});
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

// 내가 한 일
$(function() {
		$("textarea").change(function(){
			var td_con = $("#memo_td_con").val();
			var serial = $("#memoFrm").serialize();
			mergeMemo(serial);
		});
		$("#memoList").on("click", ".memoList", function(){
			var prj_id = $(this).parents().find("table").data("prj_id");
			var user_email = $(this).parents().find("table").data("memo_email");
			var dt_str = $(this).data("memo_dt_str");
			console.log(user_email);
			console.log(prj_id);
			console.log(dt_str);
			
			var memoVo = {"memo_email": user_email, "memo_dt_str": dt_str, "prj_id": prj_id};
			getMemo(memoVo);
		});
		
		$("#memoList").on("click", ".todayMemo", function(){
			todayMemo();
		});
		
		getYdTdCon();
		$("#memoList").hide();
		$("#memoDetail").hide();
	});
	
	function copyTask(btn) {
		var btn = $(btn).parent().find("#memo_con");
		btn.select();
		document.execCommand('copy');
		console.log("Copied!");
	};
	
	function mergeMemo(serialData) {
		$.ajax({
			url: "/merge",
			data: serialData,
			type: "POST",
			success: function(data) {
				console.log(data);
			}
		});
	}
	
	function getYdTdCon() {
		$.ajax({
			url: '/yd_con',
			type: "POST",
			data: $("#memoFrm").serialize(),
			success: function(data) {
				console.log(data);
				$("#memo_con").val(data.td_con.memo_con);
				$("#memo_yd_con").val(data.yd_con.memo_con);
			}
		});
	}
	
	function memoList() {
		var serial = $("#memoFrm").serialize();
		$.ajax({
			url: "/memoList",
			type: "POST",
			data: serial,
			success: function(data){
				console.log(data);
				$("#memo").hide();
				$("#memoDetail").hide();
				$("#memoList").html(data.memoList);
				$("#memoList").show();
			}
		})
	}
	function getMemo(memoVo) {
		$.ajax({
			url: "/getMemo",
			type: "POST",
			data: memoVo,
			success: function(data){
				console.log(data);
				$("#memoList").hide();
				$("#memoDetail").html(data.memo);
				$("#memoDetail").show();
			}
		})		
	}
	
	function todayMemo() {
		$("#memoList").hide();
		$("#memo").show();
	}


</script>

<style>
.socketAlram {
	background: red;
	color: white;
	position: fixed;
	right: -350px;
	top: 160px;
	width: 350px;
	padding: 25px;
	z-index: 999
}
#memoView {
display : none;
}
</style>

</head>
<body>
	<div id="socketAlert" class="socketAlram" role="alert">
		<p></p>
	</div>

	<div id="memoView">
		<div id="memo">
			<form id="memoFrm">
				<label>어제 한 일 :</label> <br>
				<textarea rows="5" cols="30" id="memo_yd_con" readonly
					style="resize: none;"></textarea>
				<br> <label>오늘 할 일 :</label> <br>
				<textarea rows="5" cols="30" name="memo_con" id="memo_con"
					style="resize: none;"></textarea>
				<br> <input type="hidden" name="memo_email"
					value="${USER_INFO.user_email }"> <input type="hidden"
					name="prj_id" value="${pro.prj_id }">
				<button type="button" onclick="copyTask(this)">복사하기</button>
				<button type="button" onclick="memoList()">목록</button>
			</form>
		</div>
		<div id="memoList"></div>
		<div id="memoDetail"></div>
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
						</select> <input type="text" name="" id="" maxlength="20"
							placeholder="검색어를 입력해주세요">
						<button type="submit" id="sch_submit" value="검색">검색</button>
					</form>
				</fieldset>
			</div>
			<!-- header search box end -->

			<div id="tnb" class="dropdown">
				<ul>
					<li onclick="myFunction()" class="dropbtn">
						<a href="#"><span class="caret color_style01">메모</span></a>
						<div id="myDropdown" class="dropdown-content">
							<c:forEach items="${projectList }" var="pro">
								<a href="#"><span class="color_style01">${pro.prj_nm }</span></a>
							</c:forEach>
						</div>
					</li>
					<li><a href="#"><span class="color_style02">타이머</span></a></li>
					<li><a href="#"><span class="color_style01">화상회의</span></a></li>
					<li><a href="#"><span class="color_style01">채팅</span>리스트</a></li>
					<li><a href="#"><span class="color_style01">${USER_INFO.user_nm}</span>님 환영합니다</a></li>
					<li><a href="#" class="icon_set">설정</a>
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