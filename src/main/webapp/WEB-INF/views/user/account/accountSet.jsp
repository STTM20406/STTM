<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
.inquiryTr:hover{
		cursor: pointer;
}
ul.tabs {
	margin: 0px;
	padding: 0px;
	list-style: none;
}

ul.tabs li {
	background: none;
	color: #222;
	display: inline-block;
	padding: 10px 15px;
	cursor: pointer;
}

ul.tabs li.current {
	color: #222;
}

.tab-content {
	display: none;
	padding: 15px;
}

.tab-content.current {
	display: inherit;
}
</style>

<script>
	
	// 슬라이드 토글 기능
	$(document).ready(function(){
		
// 		$("#passForm").slideUp(0);
// 		$("#resetPass").click(function(){
// 			$("#passForm").slideToggle("slow");
// 		});
		
// 		$("#notificationForm").slideUp(0);
// 		$("#setNotification").click(function(){
// 			$("#notificationForm").slideToggle("slow");
// 		});
		
// 		$("#userStatusForm").slideUp(0);
// 		$("#setUserStatus").click(function(){
// 			$("#userStatusForm").slideToggle("slow");
// 		});
		
		// 탭 설정
		$('ul.tabs li').click(function() {
			var tab_id = $(this).attr('data-tab');

			$('ul.tabs li').removeClass('current');
			$('.tab-content').removeClass('current');

			$(this).addClass('current');
			$("#" + tab_id).addClass('current');
		});

	});

	function funLoad(){
       var Cheight = $(window).height();
       $('#wrap').css({'height':Cheight+'px'});
   }
   window.onload = funLoad;
   window.onresize = funLoad;

   // 비밀번호 재설정
   function setPass(){
   	
	   	// 아이디,패스워드 정규식
	   	var re = /^[a-zA-Z0-9]{4,12}$/ 
	   	var user_pass = document.getElementById("user_pass");
	   	
		// 새 비밀번호 공백 일 경우
		if(passForm.user_pass1.value=="") {
		    alert("새 비밀번호를 입력해 주세요");
		    passForm.user_pass1.focus();
		    return false;
		}else if(passForm.user_pass.value==""){
			 alert("새 비밀번호를 다시 입력하여 확인해 주세요");
				 passForm.user_pass2.focus();
				 return false;
		}
		
		// 패스워드가 정규식에 안맞을 경우
		if(!check(re, user_pass1, "패스워드는 4~12자의 영문 대소문자와 숫자로만 입력")) {
		    return false;
		} else if(!check(re, user_pass, "패스워드는 4~12자의 영문 대소문자와 숫자로만 입력")){
			return false;
		}
		
		$("#btnSetAccount").on("click",function(){
			$("#passForm").submit();
		});
		
		alert("비밀번호가 업데이트 되었습니다!");
   }
   
   // 휴면계정 전환
   function inactiveAccount() {
	   $("#btnInactive").on("click",function(){
			$("#userStatusForm").submit();
			alert("회원님의 계정이 휴면 상태로 전환 되었습니다.");			
		});
	}
    
   // 알림 설정 업데이트
	function setNotice() {
		$("#btnSetNotice").on("click",function(){
				$("#noticeForm").submit();
				alert("회원님의 알림 설정이 업데이트 되었습니다.");			
		});
	}
	
   
    function check(re,what,message){
    	if(re.test(what.value)){
    		return true;
    	}
    	alert(message);
    	what.value = "";
    	what.focus();
    	// return false;
    }
    
</script>

<section class = "contents">	

<div id="container">

	<div class="sub_menu">
			<ul class="tabs">
				<li data-tab="tab-1">설정3</li>
				<li data-tab="tab-2">프로필3</li>
			</ul>
	</div>
	
	<div class="tab_con">
		
		<form id="frm01">
			
			<div id="tab-1" class="tab-content current">
			
				<div id="setAccount" >
					<!-- 비밀번호 설정 -->
					<div id="resetPass" class="loginWrap" style="background-color:cornsilk"><label>비밀번호</label>
						<form action="/setUserPass" method="post" id="passForm">
							<div class="inputField">
								<ul>
									
					<!-- 				<li> -->
					<!-- 					<label for="user_pass">임시로 보여줄 이전 비밀 번호 나중에 지울겁니다.</label> -->
					<!-- 					<label>3DE67E346CE183D3C30B7D4FA96419CD</label> -->
					<%-- 					<input type="text" id="user_email" value="${user_email}" readonly> --%>
					<%-- 					<input type="text" id="user_pass5" value="${user_pass}" readonly> --%>
					<!-- 				</li><br> -->
					
									<li>
										<label for="user_pass1">새 비밀번호</label>
										<input type="text" id="user_pass1" name="user_pass1" 
											placeholder="패스워드는 4~12자의 영문 대소문자와 숫자로만 입력" value="${user_pass}">
									</li><br>
									
									<li>
										<label for="user_pass2">새 비밀번호 확인</label>
										<input type="text" id="user_pass" name="user_pass" 
											placeholder="패스워드는 4~12자의 영문 대소문자와 숫자로만 입력" value="${user_pass}">
									</li>
									
									<li>
										<input type="button" id="btnSetAccount" onclick="setPass()" value="비밀번호 업데이트">
									</li>
									
								</ul>
							</div>
						</form>
					</div>
				
					<br><br>
				
					<!-- 알림 설정 -->   
					<div id="setNotification" class="loginWrap" style="background-color:lightsalmon"><label>알림 설정</label><br>
						<form action="" method="post" id="noticeForm">
							<div class="inputField">
								<ul>
									<!-- 프로젝트에 대한 알림 -->
									<li>
										<br><br>
										<input type="checkbox" id="notice" name="project" value="${not_cd}"> 프로젝트에 대한 알림<br>
									</li><br>
									<li>
										<input type="checkbox" id="notice" name="chat" value="${not_cd}"> 채팅 메세지 알림<br>
									</li><br>
									<li>
										<input type="checkbox" id="notice" name="inquiry" value="${not_cd}"> 1:1문의 답변 알림<br>
									</li><br>
									<li>
										<input type="checkbox" id="notice" name="work" value="${not_cd}"> 업무에 대한 알림<br>
									</li><br>
									<li>
										<input type="button" id="btnSetNotice" onclick="setNotice()" value="알림 설정 업데이트">
									</li>
								</ul>
							</div>
						</form>
					</div>
					
					<br><br>
				
					<!-- 휴면 계정 설정 -->  
					<div id="setUserStatus" class="loginWrap" style="background-color:paleturquoise"><label>휴면계정</label>
						<form action="/setUserStatus" method="post" id="userStatusForm">
							<div class="inputField">
								<ul>
									<br><br>
									<li>
										<input type="text" id="user_st" name="user_st" value="${user_st}"><br>
										<input type="button" id="btnInactive" onclick="inactiveAccount()" value="휴면 계정 전환">
										<br>룰루랄라 - by mino
									</li>
									
								</ul>
							</div>
						</form>
					</div>
				</div> 
			
			</div>
			
		</form>
		
		
		<form id="frm02">
			<div id="tab-2" class="tab-content">
				<div id="setAccount" >
					<!-- 프로필 설정 -->
					<div id="setProfile" class="loginWrap" style="background-color:cornsilk"><label>비밀번호</label>
						<form action="/setUserProfile" method="post" id="prrofileForm">
							<div class="inputField">
								<ul>
								
									<li>
										<label for="user_nm">이름</label>
										<input type="text" id="user_nm" name="user_nm" 
											placeholder="" value="${user_nm}">
									</li><br>
									
									<li>
										<label for="user_hp">전화번호</label>
										<input type="text" id="user_hp" name="user_hp" 
											placeholder="010-0000-0000" value="${user_hp}">
									</li>
									
									<li>
										<input type="button" id="btnSetProfile" onclick="setProfile()" value="프로필 업데이트">
									</li>
									
								</ul>
							</div>
						</form>
					</div>
				</div>
			</div>
		</form>
		
	</div>
	
</div>
	
</section>
