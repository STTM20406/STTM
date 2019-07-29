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
	
	<script>
		function funLoad(){
	        var Cheight = $(window).height();
	        $('#wrap').css({'height':Cheight+'px'});
	    }
	    window.onload = funLoad;
	    window.onresize = funLoad;
		
	 	// 이메일 정규식 체크
	    function validate(){
	    	
	    	// 이메일  정규식
	    	var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	    	
	    	var user_email = document.getElementById("user_email");
	    	
	    	// 이메일
			if(resetPw.user_email.value=="") {
			    alert("이메일를 입력해 주세요");
			    join.user_email.focus();
			    return false;
			}

			if(!check(re2, user_email, "적합하지 않은 이메일 형식입니다.")) {
				return false;
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
			<h2><a href="login">STTM</a></h2>
		</div>
		
		<div class="loginWrap">
			<form action="/resetPassword" method="post" id="loginForm" name="resetPw">
				<div class="inputField">
					<ul>
						<span>테스트입니다</span>
						<li>
							<label for="userId">비밀번호 재설정</label>
							<input type="text" id="user_email" name="user_email" placeholder="Please enter your Email." value="">
							<input type="hidden" id="user_nm" name="user_nm" value="">
							<input type="hidden" id="user_pass" name="user_pass" value="">
						</li>
						<li>
							<button type="submit" id="btnResetPw">제출하기</button>
						</li>
						<li class="HaveAId">
							<label>이미 계정이 있으십니까? </label>
							<a href="login"><label> 로그인</label>></a>
						</li>
						
					</ul>
				</div>
			</form>
		</div>
	</div>
</body>
</html>