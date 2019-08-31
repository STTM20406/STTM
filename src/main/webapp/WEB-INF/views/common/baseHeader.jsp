<<<<<<< HEAD
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

.hroomNm:hover{
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

   //채팅 대화 보내기
   $("#hbuttonMessage").on(
		"click",
		function(evt) {
			evt.preventDefault();
			
			if (socket.readyState !== 1)
				return;

			console.debug("socket : ", socket);
			$("#disinguishSocket").val("chatting");
			//소켓으로 보낼 정보들
			let senderNm = $('#huser_nm').val();
			let content = $('#hmsg').val();
			let senderId1 = $("#huser_email").val();
			let ct_id = $("#hct_id").val();

			if (socket) {
				let socketMsg = "chatting," + senderNm + ","
						+ content + "," + senderId1 + ","
						+ ct_id; //소켓으로 이 정보를 보냄
				console.log("sssssssmsg>>", socketMsg);
				socket.send(socketMsg);
			}
			
			$("#hmsg").val('');
			$("#hmsg").focus();
		});

   
   
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
   
   $("#memoList").on("click",function(){
	   console.log("memoList Click");
// 	   var prj_id = $(this).siblings("#prj_id").val();
// 	   console.log(prj_id);
	   
	   $("#memoFrm").attr("action","/memoList");
	   $("#memoFrm").attr("method","get");
	   $("#memoFrm").submit();
	   
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
           
            
            getYdTdCon();
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
   $('#headerFaceChat').on("click", function(){
      
        var $href = $(this).attr('href');
        layer_popup($href);
    });
   
   //화상회의방 참여하기 버튼 클릭시
   $("#hfaceBtn").on("click",function(){
		window.open('http://localhost/RTCMulticonnection/index.html', '_blank')

	});
   
   //채팅리스트 클릭시
     $('#headerChat').on("click", function(){
     
       var $href = $(this).attr('href');
       layer_popup($href);
    });
   
   
   
  //화상회의생성 다음 버튼 클릭시
   $("#headerChat_btn_next").on("click", function(){
      $(".new_proejct").animate({left:'-100%'}, 500);
      $(".select_template").animate({left:'0%'}, 500);
      
      var hprj = $("#checkProject").val();
	  projectAdmListAjax(hprj);
   });
   
   //화상회의생성 이전 버튼 클릭시
   $("#hchat_btn_prev").on("click", function(){
      $(".new_proejct").animate({left:'0%'}, 500);
      $(".select_template").animate({left:'100%'}, 500);
      
   });
   $("#hchat_btn_prev2").on("click", function(){
      $(".new_proejct").animate({left:'0%'}, 500);
      $(".select_template").animate({left:'100%'}, 500);
      
   });
   
   $(".checkSelect").on("click",function(){
      a = $('input[name="headerProject"]:checked').val();
      console.log(a);
      $("#checkProject").val(a);
   });
   
   // 타이머 레이어 창 스크립트
   $('#timerTimer').on("click", function(){
        var $href = $(this).attr('href');
        layer_popup($href);
    });
   
   //화상회의 프로젝트에 맞는 멤버 리스트 가져오기
   function projectAdmListAjax(id){
		$.ajax({
			url:"/project/projectAdmListAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"prj_id=" + id,
			success:function(data){

				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<li><input type='checkbox' name='hfriend' value='" + item.user_email + "'>" + item.user_nm + "</li>";
				});	
				
				$(".headerprj_mem_item").html(html);
			}
		});
	};
	
	
	$("#headerSelectProject").on("change",function(){
		var hprj = $("#headerSelectProject").val();
		$("#checkProject").val(hprj);
		
	});
   
	
	$("#sch_submit").on("click",function(){
		$("#searchFrm").submit();
	});
	
	//프로젝트 채팅할지 일반채팅할지 선택
	$("input:radio[name=hchatMenu]").on('change',function(){
		var menu = $("input:radio[name=hchatMenu]:checked").val();
		headerChatRoomList(menu);
		
	});
	
	//프로젝트 채팅할지 일반채팅할지 선택 Ajax
	 function headerChatRoomList(chatMenu){
		$.ajax({
			url:"/headerChatRoomAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"menu=" + chatMenu,
			success:function(data){

				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<tr>";
					html += "<td>" + item.rn + "</td>";
					html += "<td id='" + item.ct_id + "' class='hroomNm'" + " onclick='headerChatRoomClick("+item.ct_id+")'>" + item.ct_nm + "</td>";
					html += "</tr>";
				});	
				
				$("#hchatRoomList").html(html);		
			}
		});
	};
	
	
	
	
	
	
	

});
	
	
	//채팅방  멤버 띄우는 Ajax
	function chatStartMem(ct_id){
		$.ajax({
			url:"/headerChatStartMemAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"ct_id=" + ct_id,
			success:function(data){
	
				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<ul>";
					html += "<li>" + item + "</li>";
					html += "</ul>";
				});	
				
				$("#hchatMem").html(html);		
			}
		});
	}
		
		
	
	
	
	//채팅방  내용 띄우는 Ajax
	function chatStartContent(ct_id){
		$.ajax({
			url:"/headerChatStartContentAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"ct_id=" + ct_id,
			success:function(data){
				var user_email = $("#huser_email").val();
				var html = "";
				data.data.forEach(function(item, index){
					if(item.user_email != user_email){
						html += "<div class='incoming_msg'>";
						html += "<div class='received_msg'>";
						html += "<div class='received_withd_msg'>";
						html += "<p>" + item.user_nm + "</p>";
						html += "<p>" + item.ch_msg  + "</p>";
						html += "<span class='time_date'>" + item.ch_msg_dtString + "</span>";
						html += "</div>";
						html += "</div>";
						html += "</div>";
					}else{
						html += "<div class='outgoing_msg'>";
						html += "<div class='sent_msg'>";
						html += "<p>" + item.user_nm + "</p>";
						html += "<p>" + item.ch_msg  + "</p>";
						html += "<span class='time_date'>" + item.ch_msg_dtString + "</span>";
						html += "</div>";
						html += "</div>";
						
						
					}
					
					
					
					
				});	
				
				$("#hchatData").html(html);		
			}
		});
	};

	//채팅방 이름
	function chatStartName(ct_id){
		$.ajax({
			url:"/headerChatStartNameAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"ct_id=" + ct_id,
			success:function(data){
				var html = "";
				html += data.data;
				
				$("#hchatName").html(html);		
			}
		});
	};


	//채팅방 클릭하면 채팅방으로 넘어가기
	function headerChatRoomClick(ctID){
		console.log("onclick해서 id : " + ctID);
		
		//채팅하고 알림 구분하기
		$('#distinguishSocket').val("chatting");
		$(".new_proejct").animate({left:'-100%'}, 500);
	    $(".select_template").animate({left:'0%'}, 500);
		$("#hct_id").val(ctID);
	    
		chatStartMem(ctID);
		chatStartContent(ctID);
		chatStartName(ctID);
	};


$(".socketAlram").hide();

function connectNotify(){
   console.log("웹소켓알림시작하거라~~~");
   socket = new SockJS("/echo.do");
   
   socket.onopen = function() {
      console.log('Info : connection opened');

   };

   socket.onmessage = function(event) { //알림메세지 보내기@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	   
	  //알림인지 채팅인지 구분 
	  var distinguish = $("#distinguishSocket").val(); 
      console.log("ReceiveMessage: ", event.data + "\n");
      
      if(distinguish != 'chatting'){
	      var data = event.data;
	      
	      if(!data.startsWith("lst:")) {
	    	  var textsplit = data.split("*");
		      var $socketAlert = $('#socketAlert p');
		      $socketAlert.text(textsplit[0]);
		      $(".socketAlram").fadeIn(300);
		      $(".socketAlram").animate({right:"0px"}, 500);
		      $("#spanCountReset").text(textsplit[1]);
		      setTimeout(function(){
		         $(".socketAlram").fadeOut(300);
		         $(".socketAlram").animate({right:"-350px"}, 500);
		          
		      },3000);
	      } 
      
      }else if(distinguish == 'chatting'){
    	  var userId = $("#huser_email").val();
    	  var strArray = event.data.split(",");

			console.log("들어온거니strArray[0] :" + strArray[0] + "strArray[1]"
					+ strArray[1] + "strArray[2]" + strArray[2] + "userId"+userId);

			if (strArray[0] != userId) {
					var printHTML = "<div class='incoming_msg'>";
					printHTML += "<div class='received_msg'>";
					printHTML += "<div class='received_withd_msg'>";
					printHTML += "<p>" + strArray[1] + "</p>";
					printHTML += "<p>" + strArray[2] + "</p>";
					printHTML += "<span class='time_date'>" + strArray[3] + "</span></div></div></div>";
				$("#hchatData").append(printHTML);
				$("#hchatData").scrollTop($("#hchatData")[0].scrollHeight);			
				
			} else {
				var printHTML = "<div class='outgoing_msg'>";
				printHTML += "<div class='sent_msg'>";
				printHTML += "<p>" + strArray[1] + "</p>";
				printHTML += "<p>" + strArray[2] + "</p>";
				printHTML += "<span class='time_date'>" + strArray[3] + "</span></div></div></div>";
				$("#hchatData").append(printHTML);
				$("#hchatData").scrollTop($("#hchatData")[0].scrollHeight);
			}
			distinguish=null;
      }
      
      
      
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

        var $el = $(el);      //레이어의 id를 $el 변수에 저장
        var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

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
      var memArray = new Array();
      
      $("input:checkbox[name=hfriend]:checked").each(function(){
         memArray.push($(this).val());
      });
      
      console.log("memArray " + memArray);
      var a = $("#hChatMemList").val(memArray);
      
     // $("#headerChatSend").submit();
   }
</script>


</head>
<body>
	<input type="hidden" id="distinguishSocket">
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
            <br> <input type="hidden" name="memo_email" value="${USER_INFO.user_email }">
                <input type="hidden" name="prj_id" id="prj_id" value="">
            <button type="button" onclick="copyTask(this)">복사하기</button>
            <button type="button" id="memoList">목록</button>
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
               <form id="searchFrm" action="#" method="get">
                  <select id="headerSearch" name="headerSearch">
                     <option value="1">업무리스트</option>
                     <option value="2">업무명</option>
                     <option value="3">프로젝트 멤버명</option>
                  </select> <input type="text" name="headerSearchText" id="headerSearchText" maxlength="20"
                     placeholder="프로젝트 검색">
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
                     <a href="/noteWrite" class="asxz" ><span class="color_style01">쪽지보내기</span></a>
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
               <li><a href="/work/timerWorkList" id="timerTimer"><span class="color_style02">타이머</span></a></li>
               <li><a href="#layerFaceChatHeader" id="headerFaceChat"><span class="color_style01">화상회의</span></a></li>
               <li><a href="#layerChatHeader" id="headerChat"><span class="color_style01">채팅</span>리스트</a></li>
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
      </div>
   
   
<!--  화상회의 생성 레이어 팝업창 -->
<!-- <div class="dim-layer"> -->
<!--    <div class="dimBg"></div> -->
   <div id="layerFaceChatHeader" class="pop-layer">
      <div class="pop-container">
         <div class="pop-project">
            <!--content //-->
            <form action="/headerChatSend" method="post" id="headerChatSend">
               
               <input type="hidden" name="checkProject" id="checkProject">
               <div class="new_proejct">
               
               	<h2>화상회의방 참여하기</h2>
                  <a href="#" id = "hfaceBtn">화상 회의</a>
                  <div class="prj_btn">
                     <a href="javascript:;" id="headerChat_btn_next">다음</a>
                  </div>
                  
                  <h2>화상회의방 생성</h2>
                  <ul>
                     <li><label>알림 문구</label> <input
                        type="text" id="hChatText" name="hChatText" placeholder="예) 화상회의방이름 : 프로젝트1, 오후 3시부터 화상회의 시작합니다">
                     </li>
                     <li><label for="prj_mem">프로젝트 리스트</label>
                           <div class="prj_mem_list">
                              <select id="headerSelectProject" name="headerSelectProject">
                                 <c:forEach items="${headerProjectList}" var="hproject" varStatus="status">
                                    <option class="checkSelect" value="${hproject.prj_id}"> ${ hproject.prj_nm }</option>
                                    
                                 </c:forEach>
                              </select>

                           </div></li>
                  </ul>
                  
                  
               </div>
               <div class="select_template">
               	  <input type="hidden" id="hChatMemList">
                  <h2>화상회의방 멤버 선택</h2>
                  <ul>
                     <li><label for="prj_mem">멤버 선택</label>
                           <div class="prj_mem_list">
                              <ul class="headerprj_mem_item"></ul>

                           </div></li>
                  </ul>
                  <div class="prj_btn">
                     <a href="javascript:;" id="hchat_btn_prev">뒤로</a> <input
                        type="button" onclick="faceBtnSubmit();" value="알림 보내기">
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



<!-- *************채팅리스트 팝업 창*************** -->
 <div id="layerChatHeader" class="pop-layer">
      <div class="pop-container">
         <div class="pop-project">
            <!--content //-->
               
               <input type="hidden" name="checkProject" id="checkProject">
               <div class="new_proejct">
                  <input type="hidden" value="${USER_INFO.user_email }" id="huser_email">
                  <input type="hidden" value="${USER_INFO.user_nm }" id="huser_nm">
                  <input type="hidden" value="" id="hct_id">
                  <h2>채팅방 선택</h2>
                  <ul>
                  	<li><input type="radio" name="hchatMenu" value="original">일반 채팅</li>
                  	<li><input type="radio" name="hchatMenu" value="project">프로젝트 채팅</li>
                  </ul>
                  
                  <div id="hchatRoomList"> </div>
                  
               </div>
               <div class="select_template">
               	  <input type="hidden" id="hChatMemList">
                  <h2>채팅방 멤버</h2>
                  <div id="hchatMem"></div>
                  
                  <h2>채팅방 이름</h2>
                  <div id="hchatName"></div>
                  <div class="chat_room"> 
					<div class="chat_room_hd">
						<div class="mesgs">
							<div class="msg_history"  id="hchatData">
                  			</div>
                  		</div>
                  	</div>
                  </div>
                  
                  <input type="text" id="hmsg" name="hmsg" class="write_msg" placeholder="Type a message" />
			      <button class="msg_send_btn" id="hbuttonMessage" type="button"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                  
                  
                  <div class="prj_btn">
                     <a href="javascript:;" id="hchat_btn_prev2">뒤로</a> 
                  </div>
               </div>
            <div class="btn-r">
               <a href="#" class="btn-layerClose">Close</a>
            </div>
            <!--// content-->
         </div>
      </div>
   </div>
=======
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

.hroomNm:hover{
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

   //채팅 대화 보내기
   $("#hbuttonMessage").on(
		"click",
		function(evt) {
			evt.preventDefault();
			
			if (socket.readyState !== 1)
				return;

			console.debug("socket : ", socket);
			$("#disinguishSocket").val("chatting");
			//소켓으로 보낼 정보들
			let senderNm = $('#huser_nm').val();
			let content = $('#hmsg').val();
			let senderId1 = $("#huser_email").val();
			let ct_id = $("#hct_id").val();

			if (socket) {
				let socketMsg = "chatting," + senderNm + ","
						+ content + "," + senderId1 + ","
						+ ct_id; //소켓으로 이 정보를 보냄
				console.log("sssssssmsg>>", socketMsg);
				socket.send(socketMsg);
			}
			
			$("#hmsg").val('');
			$("#hmsg").focus();
		});

   
   
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
   
   $("#memoList").on("click",function(){
	   console.log("memoList Click");
// 	   var prj_id = $(this).siblings("#prj_id").val();
// 	   console.log(prj_id);
	   
	   $("#memoFrm").attr("action","/memoList");
	   $("#memoFrm").attr("method","get");
	   $("#memoFrm").submit();
	   
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
           
            
            getYdTdCon();
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
   $('#headerFaceChat').on("click", function(){
      
        var $href = $(this).attr('href');
        layer_popup($href);
    });
   
   //화상회의방 참여하기 버튼 클릭시
   $("#hfaceBtn").on("click",function(){
		window.open('http://localhost/RTCMulticonnection/index.html', '_blank')

	});
   
   //채팅리스트 클릭시
     $('#headerChat').on("click", function(){
     
       var $href = $(this).attr('href');
       layer_popup($href);
    });
   
   
   
  //화상회의생성 다음 버튼 클릭시
   $("#headerChat_btn_next").on("click", function(){
      $(".new_proejct").animate({left:'-100%'}, 500);
      $(".select_template").animate({left:'0%'}, 500);
      
      var hprj = $("#checkProject").val();
	  projectAdmListAjax(hprj);
   });
   
   //화상회의생성 이전 버튼 클릭시
   $("#hchat_btn_prev").on("click", function(){
      $(".new_proejct").animate({left:'0%'}, 500);
      $(".select_template").animate({left:'100%'}, 500);
      
   });
   $("#hchat_btn_prev2").on("click", function(){
      $(".new_proejct").animate({left:'0%'}, 500);
      $(".select_template").animate({left:'100%'}, 500);
      
   });
   
   $(".checkSelect").on("click",function(){
      a = $('input[name="headerProject"]:checked').val();
      console.log(a);
      $("#checkProject").val(a);
   });
   
   // 타이머 레이어 창 스크립트
   $('#timerTimer').on("click", function(){
        var $href = $(this).attr('href');
        layer_popup($href);
    });
   
   //화상회의 프로젝트에 맞는 멤버 리스트 가져오기
   function projectAdmListAjax(id){
		$.ajax({
			url:"/project/projectAdmListAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"prj_id=" + id,
			success:function(data){

				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<li><input type='checkbox' name='hfriend' value='" + item.user_email + "'>" + item.user_nm + "</li>";
				});	
				
				$(".headerprj_mem_item").html(html);
			}
		});
	};
	
	
	$("#headerSelectProject").on("change",function(){
		var hprj = $("#headerSelectProject").val();
		$("#checkProject").val(hprj);
		
	});
   
	
	$("#sch_submit").on("click",function(){
		$("#searchFrm").submit();
	});
	
	//프로젝트 채팅할지 일반채팅할지 선택
	$("input:radio[name=hchatMenu]").on('change',function(){
		var menu = $("input:radio[name=hchatMenu]:checked").val();
		headerChatRoomList(menu);
		
	});
	
	//프로젝트 채팅할지 일반채팅할지 선택 Ajax
	 function headerChatRoomList(chatMenu){
		$.ajax({
			url:"/headerChatRoomAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"menu=" + chatMenu,
			success:function(data){

				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<tr>";
					html += "<td>" + item.rn + "</td>";
					html += "<td id='" + item.ct_id + "' class='hroomNm'" + " onclick='headerChatRoomClick("+item.ct_id+")'>" + item.ct_nm + "</td>";
					html += "</tr>";
				});	
				
				$("#hchatRoomList").html(html);		
			}
		});
	};
	
	
	
	
	
	
	

});
	
	
	//채팅방  멤버 띄우는 Ajax
	function chatStartMem(ct_id){
		$.ajax({
			url:"/headerChatStartMemAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"ct_id=" + ct_id,
			success:function(data){
	
				var html = "";
				data.data.forEach(function(item, index){
					//html 생성
					html += "<ul>";
					html += "<li>" + item + "</li>";
					html += "</ul>";
				});	
				
				$("#hchatMem").html(html);		
			}
		});
	}
		
		
	
	
	
	//채팅방  내용 띄우는 Ajax
	function chatStartContent(ct_id){
		$.ajax({
			url:"/headerChatStartContentAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"ct_id=" + ct_id,
			success:function(data){
				var user_email = $("#huser_email").val();
				var html = "";
				data.data.forEach(function(item, index){
					if(item.user_email != user_email){
						html += "<div class='incoming_msg'>";
						html += "<div class='received_msg'>";
						html += "<div class='received_withd_msg'>";
						html += "<p>" + item.user_nm + "</p>";
						html += "<p>" + item.ch_msg  + "</p>";
						html += "<span class='time_date'>" + item.ch_msg_dtString + "</span>";
						html += "</div>";
						html += "</div>";
						html += "</div>";
					}else{
						html += "<div class='outgoing_msg'>";
						html += "<div class='sent_msg'>";
						html += "<p>" + item.user_nm + "</p>";
						html += "<p>" + item.ch_msg  + "</p>";
						html += "<span class='time_date'>" + item.ch_msg_dtString + "</span>";
						html += "</div>";
						html += "</div>";
						
						
					}
					
					
					
					
				});	
				
				$("#hchatData").html(html);		
			}
		});
	};

	//채팅방 이름
	function chatStartName(ct_id){
		$.ajax({
			url:"/headerChatStartNameAjax",
			method:"post",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:	"ct_id=" + ct_id,
			success:function(data){
				var html = "";
				html += data.data;
				
				$("#hchatName").html(html);		
			}
		});
	};


	//채팅방 클릭하면 채팅방으로 넘어가기
	function headerChatRoomClick(ctID){
		console.log("onclick해서 id : " + ctID);
		
		//채팅하고 알림 구분하기
		$('#distinguishSocket').val("chatting");
		$(".new_proejct").animate({left:'-100%'}, 500);
	    $(".select_template").animate({left:'0%'}, 500);
		$("#hct_id").val(ctID);
	    
		chatStartMem(ctID);
		chatStartContent(ctID);
		chatStartName(ctID);
	};


$(".socketAlram").hide();

function connectNotify(){
   console.log("웹소켓알림시작하거라~~~");
   socket = new SockJS("/echo.do");
   
   socket.onopen = function() {
      console.log('Info : connection opened');

   };

   socket.onmessage = function(event) { //알림메세지 보내기@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	   
	  //알림인지 채팅인지 구분 
	  var distinguish = $("#distinguishSocket").val(); 
      console.log("ReceiveMessage: ", event.data + "\n");
      
      if(distinguish != 'chatting'){
	      var data = event.data;
	      
	      if(!data.startsWith("lst:")) {
		      var $socketAlert = $('#socketAlert p');
		      $socketAlert.text(event.data);
		      $(".socketAlram").fadeIn(300);
		      $(".socketAlram").animate({right:"0px"}, 500);
		      setTimeout(function(){
		         $(".socketAlram").fadeOut(300);
		         $(".socketAlram").animate({right:"-350px"}, 500);
		          
		      },3000);
	      } 
      
      }else if(distinguish == 'chatting'){
    	  var userId = $("#huser_email").val();
    	  var strArray = event.data.split(",");

			console.log("들어온거니strArray[0] :" + strArray[0] + "strArray[1]"
					+ strArray[1] + "strArray[2]" + strArray[2] + "userId"+userId);

			if (strArray[0] != userId) {
					var printHTML = "<div class='incoming_msg'>";
					printHTML += "<div class='received_msg'>";
					printHTML += "<div class='received_withd_msg'>";
					printHTML += "<p>" + strArray[1] + "</p>";
					printHTML += "<p>" + strArray[2] + "</p>";
					printHTML += "<span class='time_date'>" + strArray[3] + "</span></div></div></div>";
				$("#hchatData").append(printHTML);
				$("#hchatData").scrollTop($("#hchatData")[0].scrollHeight);			
				
			} else {
				var printHTML = "<div class='outgoing_msg'>";
				printHTML += "<div class='sent_msg'>";
				printHTML += "<p>" + strArray[1] + "</p>";
				printHTML += "<p>" + strArray[2] + "</p>";
				printHTML += "<span class='time_date'>" + strArray[3] + "</span></div></div></div>";
				$("#hchatData").append(printHTML);
				$("#hchatData").scrollTop($("#hchatData")[0].scrollHeight);
			}
			distinguish=null;
      }
      
      
      
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

        var $el = $(el);      //레이어의 id를 $el 변수에 저장
        var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

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
      var memArray = new Array();
      
      $("input:checkbox[name=hfriend]:checked").each(function(){
         memArray.push($(this).val());
      });
      
      console.log("memArray " + memArray);
      var a = $("#hChatMemList").val(memArray);
      
     // $("#headerChatSend").submit();
   }
</script>


</head>
<body>
	<input type="hidden" id="distinguishSocket">
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
            <br> <input type="hidden" name="memo_email" value="${USER_INFO.user_email }">
                <input type="hidden" name="prj_id" id="prj_id" value="">
            <button type="button" onclick="copyTask(this)">복사하기</button>
            <button type="button" id="memoList">목록</button>
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
               <form id="searchFrm" action="#" method="get">
                  <select id="headerSearch" name="headerSearch">
                     <option value="1">업무리스트</option>
                     <option value="2">업무명</option>
                     <option value="3">프로젝트 멤버명</option>
                  </select> <input type="text" name="headerSearchText" id="headerSearchText" maxlength="20"
                     placeholder="프로젝트 검색">
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
                     <a href="/noteWrite" class="asxz" ><span class="color_style01">쪽지보내기</span></a>
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
               <li><a href="/work/timerWorkList" id="timerTimer"><span class="color_style02">타이머</span></a></li>
               <li><a href="#layerFaceChatHeader" id="headerFaceChat"><span class="color_style01">화상회의</span></a></li>
               <li><a href="#layerChatHeader" id="headerChat"><span class="color_style01">채팅</span>리스트</a></li>
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
      </div>
   
   
<!--  화상회의 생성 레이어 팝업창 -->
<!-- <div class="dim-layer"> -->
<!--    <div class="dimBg"></div> -->
   <div id="layerFaceChatHeader" class="pop-layer">
      <div class="pop-container">
         <div class="pop-project">
            <!--content //-->
            <form action="/headerChatSend" method="post" id="headerChatSend">
               
               <input type="hidden" name="checkProject" id="checkProject">
               <div class="new_proejct">
               
               	<h2>화상회의방 참여하기</h2>
                  <a href="#" id = "hfaceBtn">화상 회의</a>
                  <div class="prj_btn">
                     <a href="javascript:;" id="headerChat_btn_next">다음</a>
                  </div>
                  
                  <h2>화상회의방 생성</h2>
                  <ul>
                     <li><label>알림 문구</label> <input
                        type="text" id="hChatText" name="hChatText" placeholder="예) 화상회의방이름 : 프로젝트1, 오후 3시부터 화상회의 시작합니다">
                     </li>
                     <li><label for="prj_mem">프로젝트 리스트</label>
                           <div class="prj_mem_list">
                              <select id="headerSelectProject" name="headerSelectProject">
                                 <c:forEach items="${headerProjectList}" var="hproject" varStatus="status">
                                    <option class="checkSelect" value="${hproject.prj_id}"> ${ hproject.prj_nm }</option>
                                    
                                 </c:forEach>
                              </select>

                           </div></li>
                  </ul>
                  
                  
               </div>
               <div class="select_template">
               	  <input type="hidden" id="hChatMemList">
                  <h2>화상회의방 멤버 선택</h2>
                  <ul>
                     <li><label for="prj_mem">멤버 선택</label>
                           <div class="prj_mem_list">
                              <ul class="headerprj_mem_item"></ul>

                           </div></li>
                  </ul>
                  <div class="prj_btn">
                     <a href="javascript:;" id="hchat_btn_prev">뒤로</a> <input
                        type="button" onclick="faceBtnSubmit();" value="알림 보내기">
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



<!-- *************채팅리스트 팝업 창*************** -->
 <div id="layerChatHeader" class="pop-layer">
      <div class="pop-container">
         <div class="pop-project">
            <!--content //-->
               
               <input type="hidden" name="checkProject" id="checkProject">
               <div class="new_proejct">
                  <input type="hidden" value="${USER_INFO.user_email }" id="huser_email">
                  <input type="hidden" value="${USER_INFO.user_nm }" id="huser_nm">
                  <input type="hidden" value="" id="hct_id">
                  <h2>채팅방 선택</h2>
                  <ul>
                  	<li><input type="radio" name="hchatMenu" value="original">일반 채팅</li>
                  	<li><input type="radio" name="hchatMenu" value="project">프로젝트 채팅</li>
                  </ul>
                  
                  <div id="hchatRoomList"> </div>
                  
               </div>
               <div class="select_template">
               	  <input type="hidden" id="hChatMemList">
                  <h2>채팅방 멤버</h2>
                  <div id="hchatMem"></div>
                  
                  <h2>채팅방 이름</h2>
                  <div id="hchatName"></div>
                  <div class="chat_room"> 
					<div class="chat_room_hd">
						<div class="mesgs">
							<div class="msg_history"  id="hchatData">
                  			</div>
                  		</div>
                  	</div>
                  </div>
                  
                  <input type="text" id="hmsg" name="hmsg" class="write_msg" placeholder="Type a message" />
			      <button class="msg_send_btn" id="hbuttonMessage" type="button"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                  
                  
                  <div class="prj_btn">
                     <a href="javascript:;" id="hchat_btn_prev2">뒤로</a> 
                  </div>
               </div>
            <div class="btn-r">
               <a href="#" class="btn-layerClose">Close</a>
            </div>
            <!--// content-->
         </div>
      </div>
   </div>
>>>>>>> 92a047d5b1d5ffd6506be989a7f2f49f709723b6
