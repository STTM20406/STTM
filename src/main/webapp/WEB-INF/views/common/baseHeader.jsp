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
<link href="http://www.cssscript.com/wp-includes/css/sticky.css" rel="stylesheet" type="text/css">
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
.dropdown-Notecontent {
	display: none;
	position: absolute;
	background-color: #f1f1f1;
	min-width: 160px;
	overflow: auto;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-Notecontent a {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
}

.show {
	display: block;
}

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

/* 타이머 스타일 */
@import url('https://fonts.googleapis.com/css?family=Roboto:100,300');

button[data-setter] {
  outline: none;
  background: transparent;
  border: none;
  font-family: 'Roboto';
  font-weight: 300;
  font-size: 18px;
  width: 25px;
  height: 30px;
  color: #F7958E;
  cursor: pointer;
}

button[data-setter]:hover { opacity: 0.5; }

.container1 {
  position: relative;
  top: 30px;
  width: 300px;
  margin: 0 auto;
}

.setters {
  position: absolute;
  left: 85px;
  top: 75px;
}

.minutes-set {
  float: left;
  margin-right: 28px;
}

.seconds-set { float: right; }

.controlls {
  position: absolute;
  left: 75px;
  top: 105px;
  text-align: center;
}

.display-remain-time {
  font-family: 'Roboto';
  font-weight: 100;
  font-size: 65px;
  color: #F7958E;
}

#pause {
  outline: none;
  background: transparent;
  border: none;
  margin-top: 10px;
  width: 50px;
  height: 50px;
  position: relative;
}

.play::before {
  display: block;
  content: "";
  position: absolute;
  top: 8px;
  left: 16px;
  border-top: 15px solid transparent;
  border-bottom: 15px solid transparent;
  border-left: 22px solid #F7958E;
}

.pause::after {
  content: "";
  position: absolute;
  top: 8px;
  left: 12px;
  width: 15px;
  height: 30px;
  background-color: transparent;
  border-radius: 1px;
  border: 5px solid #F7958E;
  border-top: none;
  border-bottom: none;
}

#pause:hover { opacity: 0.8; }

.e-c-base {
  fill: none;
  stroke: #B6B6B6;
  stroke-width: 4px
}

.e-c-progress {
  fill: none;
  stroke: #F7958E;
  stroke-width: 4px;
  transition: stroke-dashoffset 0.7s;
}

.e-c-pointer {
  fill: #FFF;
  stroke: #F7958E;
  stroke-width: 2px;
}

#e-pointer { transition: transform 0.7s; }
h1 { margin-top:150px; text-align:center;}
body { background-color:#f7f7f7;}
</style>

<script>
var socket = null;

function copyTask(btn) {
	var btn = $(btn).parent().find("#memo_con");
	btn.select();
	document.execCommand('copy');
	console.log("Copied!");
};
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
	
	
	// 내가 한 일
	$(".memoA").on("click",function(){
		console.log("CLICKCLICK");
		var a = $(this).siblings("#memoPrj_id").val();
		var nm = $(this).siblings("#memoPrj_nm").val();
		console.log(a);
		console.log(nm);
		var b = $("#prj_id").val(a);
		var prjNm = $("#h2prj_nm").text(nm);
		
			
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
						$("#memo_yd_con").val(data.yd_con.memo_con);
						$("#memo_con").val(data.td_con.memo_con);
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
		
		
		$("#memoView").animate({right:'0'}, 500);
	})
	
	//프로젝트 닫기 버튼을 클릭했을 때
	$(".btnSetClose").on("click", function(){
		$("#memoView").animate({right:'-700px'}, 500);
	});
	
	//화상회의생성 버튼 클릭시
	$('#chat').on("click", function(){
		
        var $href = $(this).attr('href');
        layer_popup($href);
    });
	
	//화상회의생성 다음 버튼 클릭시
	$("#prj_btn_next").on("click", function(){
		$(".new_proejct").animate({left:'-100%'}, 500);
		$(".select_template").animate({left:'0%'}, 500);
	});
	
	//화상회의생성 이전 버튼 클릭시
	$("#prj_btn_prev").on("click", function(){
		$(".new_proejct").animate({left:'0%'}, 500);
		$(".select_template").animate({left:'100%'}, 500);
		
	});
	
	$(".checkSelect").on("click",function(){
		a = $('input[name="projectRadio"]:checked').val();
		console.log(a);
		$("#checkProject").val(a);
	});
	
	// 타이머 레이어 창 스크립트
	$('#timerTimer').on("click", function(){
        var $href = $(this).attr('href');
        layer_popup($href);
    });
	
	
	
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
// 새로추가한 쪽지 dropdown
function myFunctionNote() {
  	$(".dropdown-Notecontent").fadeIn(300);
	$(".dropdown-Notecontent").on("mouseleave", function(){
		$(this).fadeOut(300);
	});
}

window.onclick = function(event) {
  if (!event.target.matches('.dropNotebtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-Notecontent");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

	//layer popup - 화상회의방 생성
	function layer_popup(el){
		console.log(el);

        var $el = $(el);		//레이어의 id를 $el 변수에 저장
        var isDim = $el.prev().hasClass('dimBg');	//dimmed 레이어를 감지하기 위한 boolean 변수

        isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

        var $elWidth = ~~($el.outerWidth()),
            $elHeight = ~~($el.outerHeight()),
            docWidth = $(document).width(),
            docHeight = $(document).height();

        // 화면의 중앙에 레이어를 띄운다.
        if ($elHeight < docHeight || $elWidth < docWidth) {
            $el.css({
                marginTop: -$elHeight /2,
                marginLeft: -$elWidth/2
            })
        } else {
            $el.css({top: 0, left: 0});
        }

        $el.find('a.btn-layerClose').click(function(){
            isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
            return false;
        });

        $('.layer .dimBg').click(function(){
            $('.dim-layer').fadeOut();
            return false;
        });

    }
	
	//화상회의값 보내기 - 선택한멤버리스트 함께 넘기기
	function faceBtnSubmit(){
		var memArray = [];
		$("input[name=projectRadio]:checked").each(function(){
			memArray.push($(this).val());
		});
		var a = $("#memList1").val(memArray);
		
		$("#prj_insert").submit();
	}
</script>


</head>
<body>
	<div id="socketAlert" class="socketAlram" role="alert">
		<p></p>
	</div>

	<div id="memoView">
		<div id="memo">
			<form id="memoFrm">
				<h1 id="h2prj_nm"></h1>
				<label>어제 한 일 </label> <br>
				<textarea rows="5" cols="30" id="memo_yd_con" readonly
					style="resize: none;"></textarea>
				<br> <label>오늘 할 일 </label> <br>
				<textarea rows="5" cols="30" name="memo_con" id="memo_con"
					style="resize: none;"></textarea>
				<br> <input type="hidden" name="memo_email"
					value="${USER_INFO.user_email }"> <input type="hidden"
					name="prj_id" id="prj_id" value="">
				<button type="button" onclick="copyTask(this)">복사하기</button>
				<button type="button" onclick="memoList()">목록</button>
			</form>
		</div>
		<div id="memoList"></div>
		<div id="memoDetail"></div>
		<div class="btnSetClose">닫기</div>
		
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
					<li onclick="myFunctionNote()" class="dropNotebtn">
						<a href="#"><span class="caret color_style01">쪽지</span></a>
						<div id="myDropdown" class="dropdown-Notecontent">
							<a href="#" class="asxz" ><span class="color_style01">쪽지보내기</span></a>
							<a href="/noteList" class="asdfw" ><span class="color_style01">쪽지함</span></a>
						</div>
					</li>
					<li onclick="myFunction()" class="dropbtn">
						<a href="#"><span class="caret color_style01">메모</span></a>
						<div id="myDropdown" class="dropdown-content">
							<c:forEach items="${projectList }" var="pro">
								<div>
									<a href="#" class="memoA" ><span class="color_style01">${pro.prj_nm }</span></a>
									<input type="hidden" id="memoPrj_id" value="${pro.prj_id }"/>
									<input type="hidden" id="memoPrj_nm" value="${pro.prj_nm }"/>
								</div>
							</c:forEach>
						</div>
					</li>
					<li><a href="#timer" id="timerTimer"><span class="color_style02">타이머</span></a></li>
					<li><a href="#layerChatHeader" id="chat"><span class="color_style01">화상회의</span></a></li>
					<li><a href="#"><span class="color_style01">채팅</span>리스트</a></li>
					<li><a href="#" class="icon_set"><span class="color_style01">${USER_INFO.user_nm}</span>님 환영합니다</a>
						<div class="user_set_list">
							<dl>
								<dt></dt>
								<dd><a href="/setUserPass">계정설정</a></dd>
								<dd><a href="/setUserProfile">프로필설정</a></dd>
								<dd><a href="/individualBox">개인보관함</a></dd>
								<dd><a href="/logout">로그아웃</a></dd>
							</dl>
						</div>
					</li>
				</ul>
			</div>
		</header>
	
	
<!--  화상회의 생성 레이어 팝업창 -->
<!-- <div class="dim-layer"> -->
<!-- 	<div class="dimBg"></div> -->
	<div id="layerChatHeader" class="pop-layer">
		<div class="pop-container">
			<div class="pop-project">
				<!--content //-->
				<form action="/chatSend" method="post" id="chatSend">
					<input type="hidden" name="memList1" id="memList1" value="">
					<input type="hidden" name="checkProject" id="checkProject">
					<div class="new_proejct">
						<h2>화상회의방 생성</h2>
						<ul>
							<li><label>화상회의방 이름</label> <input
								type="text" id="prj_nm" name="prj_nm" placeholder="예) 프로젝트1">
							</li>
							<li><label for="prj_mem">프로젝트 리스트</label>
									<div class="prj_mem_list">
										<ul>
											<c:forEach items="${projectList}" var="project"
												varStatus="status">
												<li><input type="radio" id="projectRadio" name="projectRadio"
													class="checkSelect" value="${project.prj_id}">${project.prj_id} ${ project.prj_nm }
												</li>
											</c:forEach>
										</ul>

									</div></li>
						</ul>
						<div class="prj_btn">
							<a href="javascript:;" id="prj_btn_next">다음 : 템플릿 선택</a>
						</div>
					</div>
					<div class="select_template">
						<h2>화상회의방 멤버 선택</h2>
						<ul>
							<li><label for="prj_mem">멤버 선택</label>
									<div class="prj_mem_list">
										<ul>
											<c:forEach items="${headerChatFriendList}" var="memlist1" varStatus="status">
												<c:if test="${memlist1.prj_id == 1  }">
														<li><input type="checkbox" name="friend"
															class="checkSelect1" value="${memlist1.prj_id}">${memlist1.user_nm }
														</li>
												</c:if>
											</c:forEach>
										</ul>

									</div></li>
						</ul>
						<div class="prj_btn">
							<a href="javascript:;" id="prj_btn_prev">뒤로</a> <input
								type="button" onclick="faceBtnSubmit();" value="프로젝트 만들기">
						</div>
					</div>
				</form>
				<div class="btn-r">
					<a href="#" class="btn-layerClose">Close</a>
				</div>
				<!--// content-->
			</div>
		</div>
	</div>
<!-- </div> -->

<!-----------------------------------  타이머 레이어 팝업창 ----------------------------------->
<div id="timer" class="pop-layer">
    <div class="pop-container">
        <div class="pop-conts">
<!------------------------------  timer content ------------------------------>
            <p class="ctxt mb20">
			타이머 가져오나
			</p>
			
<h1>Circular Countdown Timer Demo</h1>
<div class="container">
  <div class="setters">
    <div class="minutes-set">
      <button data-setter="minutes-plus">+</button>
      <button data-setter="minutes-minus">-</button>
    </div>
    <div class="seconds-set">
      <button data-setter="seconds-plus">+</button>
      <button data-setter="seconds-minus">-</button>
    </div>
  </div>
  <div class="circle"> <svg width="300" viewBox="0 0 220 220" xmlns="http://www.w3.org/2000/svg">
    <g transform="translate(110,110)">
      <circle r="100" class="e-c-base"/>
      <g transform="rotate(-90)">
        <circle r="100" class="e-c-progress"/>
        <g id="e-pointer">
          <circle cx="100" cy="0" r="8" class="e-c-pointer"/>
        </g>
      </g>
    </g>
    </svg> </div>
  <div class="controlls">
    <div class="display-remain-time">00:30</div>
    <button class="play" id="pause"></button>
  </div>
</div>
<script>
//circle start
let progressBar = document.querySelector('.e-c-progress');
let indicator = document.getElementById('e-indicator');
let pointer = document.getElementById('e-pointer');
let length = Math.PI * 2 * 100;

progressBar.style.strokeDasharray = length;

function update(value, timePercent) {
	var offset = - length - length * value / (timePercent);
	progressBar.style.strokeDashoffset = offset; 
	pointer.style.transform = `rotate(${360 * value / (timePercent)}deg)`; 
};

//circle ends
const displayOutput = document.querySelector('.display-remain-time')
const pauseBtn = document.getElementById('pause');
const setterBtns = document.querySelectorAll('button[data-setter]');

let intervalTimer;
let timeLeft;
let wholeTime = 0.5 * 60; // manage this to set the whole time 
let isPaused = false;
let isStarted = false;


update(wholeTime,wholeTime); //refreshes progress bar
displayTimeLeft(wholeTime);

function changeWholeTime(seconds){
  if ((wholeTime + seconds) > 0){
    wholeTime += seconds;
    update(wholeTime,wholeTime);
  }
}


for (var i = 0; i < setterBtns.length; i++) {
    setterBtns[i].addEventListener("click", function(event) {
        var param = this.dataset.setter;
        switch (param) {
            case 'minutes-plus':
                changeWholeTime(1 * 60);
                break;
            case 'minutes-minus':
                changeWholeTime(-1 * 60);
                break;
            case 'seconds-plus':
                changeWholeTime(1);
                break;
            case 'seconds-minus':
                changeWholeTime(-1);
                break;
        }
      displayTimeLeft(wholeTime);
    });
}

function timer (seconds){ //counts time, takes seconds
  let remainTime = Date.now() + (seconds * 1000);
  displayTimeLeft(seconds);
  
  intervalTimer = setInterval(function(){
    timeLeft = Math.round((remainTime - Date.now()) / 1000);
    if(timeLeft < 0){
      clearInterval(intervalTimer);
      isStarted = false;
      setterBtns.forEach(function(btn){
        btn.disabled = false;
        btn.style.opacity = 1;
      });
      displayTimeLeft(wholeTime);
      pauseBtn.classList.remove('pause');
      pauseBtn.classList.add('play');
      return ;
    }
    displayTimeLeft(timeLeft);
  }, 1000);
}
function pauseTimer(event){
  if(isStarted === false){
    timer(wholeTime);
    isStarted = true;
    this.classList.remove('play');
    this.classList.add('pause');
    
    setterBtns.forEach(function(btn){
      btn.disabled = true;
      btn.style.opacity = 0.5;
    });

  }else if(isPaused){
    this.classList.remove('play');

    this.classList.add('pause');
    timer(timeLeft);
    isPaused = isPaused ? false : true
  }else{
    this.classList.remove('pause');
    this.classList.add('play');
    clearInterval(intervalTimer);
    isPaused = isPaused ? false : true ;
  }
}

function displayTimeLeft (timeLeft){ //displays time on the input
  let minutes = Math.floor(timeLeft / 60);
  let seconds = timeLeft % 60;
  let displayString = `${minutes < 10 ? '0' : ''}${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
  displayOutput.textContent = displayString;
  update(timeLeft, wholeTime);
}

pauseBtn.addEventListener('click',pauseTimer);

</script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-46156385-1', 'cssscript.com');
  ga('send', 'pageview');

</script>



            <div class="btn-r">
                <a href="#" class="btn-layerClose">Close</a>
            </div>
<!------------------------------  timer content ------------------------------>
        </div>
    </div>
</div>
<!-----------------------------------  타이머 레이어 팝업창 ----------------------------------->