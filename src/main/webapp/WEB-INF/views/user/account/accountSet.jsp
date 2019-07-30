<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
	
	// 슬라이드 토글 기능
	$(document).ready(function(){
		
		$("#passForm").slideUp(0);
		$("#resetPass").click(function(){
			$("#passForm").slideToggle("slow");
		});
		
		$("#notificationForm").slideUp(0);
		$("#setNotification").click(function(){
			$("#notificationForm").slideToggle("slow");
		});
		
		$("#userStatusForm").slideUp(0);
		$("#setUserStatus").click(function(){
			$("#userStatusForm").slideToggle("slow");
		});


	function funLoad(){
       var Cheight = $(window).height();
       $('#wrap').css({'height':Cheight+'px'});
   }
   window.onload = funLoad;
   window.onresize = funLoad;

   // 회원가입 정규식
   function regPass(){
   	
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
   
   function inactiveAccount() {
	   $("#btnInactive").on("click",function(){
			$("#userStatusForm").submit();
			alert("회원님의 계정이 휴면 상태로 전환 되었습니다.");			
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
<div id="setAccount" ><label>설정</label>
		
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
					<input type="button" id="btnSetAccount" onclick="regPass()" value="비밀번호 업데이트">
				</li>
				
			</ul>
		</div>
	</form>
</div>

<br><br>

<!-- 알림 설정 -->   
<div id="setNotification" class="loginWrap" style="background-color:lightsalmon"><label>알림 설정</label><br>
	<form action="" method="post" id="notificationForm">
		<div class="inputField">
			<ul>
				<!-- 프로젝트에 대한 알림 -->
				<li>
					<br><br>
					<input type="checkbox" id="" name="" value=""> 프로젝트에 대한 알림<br>
				</li><br>
				
				<li>
					<input type="checkbox" id="" name="" value=""> 채팅 메세지 알림<br>

				</li><br>
				
				<li>
					<input type="checkbox" id="" name="" value=""> 1:1문의 답변 알림<br>
				</li><br>
				
				<li>
					<input type="checkbox" id="" name="" value=""> 업무에 대한 알림<br>
				</li><br>
				
				<li>
					<input type="button" id="btnSetNotice">알림 설정 업데이트</button>
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
					</li>
					
				</ul>
			</div>
		</form>
	</div>

</div>
	
</section>
