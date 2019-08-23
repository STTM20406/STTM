<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="ko">
<head>
	<title>register</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<link rel="stylesheet" type="text/css" href="../css/default.css">
	<link rel="stylesheet" type="text/css" href="../css/member.css">
	<script src="../js/jquery-1.8.3.min.js"></script>
	
	<!-- 구글 로그인 시작 : 구글 플랫폼 라이브러리 -->
	<script src="https://apis.google.com/js/platform.js" async defer></script>
	
	<!-- content에 클라이언트 아이디 지정 -->
	<meta name="google-signin-client_id" content="1007667076011-rnn1ucbiirf2uqshgan4a62rjthqigbk.apps.googleusercontent.com">
	
	<script>
		function funLoad(){
	        var Cheight = $(window).height();
	        $('#wrap').css({'height':Cheight+'px'});
	    }
	    window.onload = funLoad;
	    window.onresize = funLoad;

// 	    function send(){
// 	    	window.location = "./sendEmail.html";
// 	    	window.location = "sendEmail";
// 	    }
		
		// 구글 사용자 정보 얻어오기
		function onSignIn(googleUser) {
		  var profile = googleUser.getBasicProfile();
		  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
		  console.log('Name: ' + profile.getName());
		  console.log('Image URL: ' + profile.getImageUrl());
		  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
		}
		// 약관 체크 했는데 검사
		function termsCheck(join){
			
			// 체크 박스 체크여부 확인
			var check = document.sendEmailForm.infoCheck.checked;
			
		    if(!check){
	            alert('이용약관과 개인정보보호 정책에 동의 해주세요.');
	            return false;
	        } 
			
		}
		
	</script>
	
</head>
<body>
	<div id="wrap" class="wrap_bg_01">
		<div class="loginLogo">
			<h2><a href="login">STTM</a></h2>
		</div>
		<div class="registerBtn">
			<ul>
				<li>
					<span>이미 회원이신가요?</span>
					<a href="login">Login</a>
				</li>
			</ul>
		</div>
		
		<div class="loginWrap">
			<form action="/register" method="post" id="sendEmailForm" name="sendEmailForm" onsubmit="return termsCheck(this)">
				<div class="inputField">
					<ul>
						
						<li>
							<label for="userId">USER EMAIL</label>
							<input type="text" id="user_email" name="user_email" placeholder="Please enter your Email." value="">
						</li>
						<li>
							<dl class="formList">
								<dt class="blind">약관동의</dt>
								<dd>
									<div class="checkList etrans">
										<input type="checkbox" id="infoCheck">
										<label for="infoCheck">이용약관과 개인정보보호 정책에 동의 합니다</label>
									</div>
								</dd>
							</dl>
						</li>
						<li>
							<label for="userId">GOOGLE EMAIL로 가입하기</label>
							
							<!-- 구글 로그인 버튼 만들기 -->
							<div class="g-signin2" data-onsuccess="onSignIn"></div>
						</li>
						<li>
							<button type="submit" id="btnLogin">Sign Up</button>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</div>
</body>
</html>