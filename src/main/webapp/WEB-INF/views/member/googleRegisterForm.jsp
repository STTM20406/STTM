<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html lang="ko">
<head>
	<title>login</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<link rel="stylesheet" type="text/css" href="../css/default.css">
	<link rel="stylesheet" type="text/css" href="../css/member.css">
	<script src="../js/jquery-1.8.3.min.js"></script>

	<script>
		
		function funLoad(){
	        var Cheight = $(window).height();
	        $('#wrap').css({'height':Cheight+'px'});
	    }
	    window.onload = funLoad;
	    window.onresize = funLoad;
		
	    function validate(){
	    	
	    	// 아이디,패스워드 정규식
	    	var re = /^[a-zA-Z0-9]{4,12}$/ 
	    	// 이메일  정규식
	    	var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			
	    	var user_email = document.getElementById("user_email");
	    	var user_nm = document.getElementById("user_nm");
	    	var user_pass = document.getElementById("user_pass");
	    	var user_hp = document.getElementById("user_hp");
	    	
	    	if(!check){
	    		
	    	}
	    	
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
</head>
<body>
	<div id="wrap" class="wrap_bg_01">
		<div class="loginLogo">
			<h2><a href="">STTM</a></h2>
		</div>
		
		<div class="registerBtn">
			<ul>
				<li>
					<span>아직 계정이 없나요?</span>
					<a href="register">SignUp</a>
				</li>
			</ul>
		</div>
		
		<div class="registerFormWrap">
			<form action="" method="post" id="registerForm">
				<div class="inputField">
					<ul>
						<li>
							<label for="userEmail">이메일</label>
							<input type="text" id="userEmail" name="user_email" value="${user_email}">
						</li>
						<li>
							<label for="userName">이름</label>
							<input type="text" id="userName" name="user_nm">
						</li>
						<li>
							<label for="userPass">비밀번호</label>
							<input type="password" id="userPass" name="user_pass">
						</li>
						<li>
							<label for="userPh">핸드폰번호</label>
							<input type="password" id="userPh" name="user_hp">
						</li>
						<li><button type="submit" id="btnLogin">가입하기</button></li>
					</ul>
				<div class="inputField">
			</form>
		</div>
	</div>
</body>
</html>
