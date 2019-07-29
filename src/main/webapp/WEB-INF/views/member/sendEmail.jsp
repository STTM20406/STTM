<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="ko">
<head>
	<title>sendEmail</title>
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

	</script>
</head>
<body>
	<div id="wrap" class="wrap_bg_02">
		
		<div class="loginLogo">
			<h2><a href="login">STTM</a></h2>
		</div>
		<div class="sendText">
			<h2>아래의 주소로 이메일을 발송했습니다<span>${user_email}</span></h2>
			<p>
				STTM을 이용해주셔서 감사합니다. 해당 이메일을 확인해 주세요.
				<span>이메일을 아직 못 받으셨나요?</span>
			</p>
			<a href="">인증 이메일 다시 보내기</a>
		</div>
	</div>
</body>
</html>