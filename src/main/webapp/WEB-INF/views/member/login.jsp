<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html lang="ko">
<head>

<!-- 구글 로그인 API 시작 -->
<meta name="google-signin-scope" content="profile email">
<!-- Client ID : Google에 신청해 발급받은 ID를 content의 값으로 입력 -->
<meta name="google-signin-client_id"
	content="1007667076011-rnn1ucbiirf2uqshgan4a62rjthqigbk.apps.googleusercontent.com">
<script src="https://apis.google.com/js/platform.js" async defer></script>

<!-- 구글 로그인 API 끝 -->

<title>login</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link rel="stylesheet" type="text/css" href="../css/default.css">
<link rel="stylesheet" type="text/css" href="../css/member.css">
<script src="../js/jquery-1.8.3.min.js"></script>

<script>
	function funLoad() {
		var Cheight = $(window).height();
		$('#wrap').css({
			'height' : Cheight + 'px'
		});
	}
	window.onload = funLoad;
	window.onresize = funLoad;
</script>

<!-- 구글 API 관련 script -->
<script>
	function onSignIn(googleUser) {
		// Useful data for your client-side scripts:
		var profile = googleUser.getBasicProfile();
		console.log("ID: " + profile.getId()); // ID값을 이용하려면 최소한 암호화 처리는 할 것.
		console.log('Full Name: ' + profile.getName());
		console.log('Given Name: ' + profile.getGivenName());
		console.log('Family Name: ' + profile.getFamilyName());
		console.log("Image URL: " + profile.getImageUrl());
		console.log("Email: " + profile.getEmail());

		// The ID token you need to pass to your backend:
		var id_token = googleUser.getAuthResponse().id_token;
		console.log("ID Token: " + id_token);
	}

	function signOut() {
		var auth2 = gapi.auth2.getAuthInstance();
		auth2.signOut().then(function() {
			console.log('User signed out.');
		});
		auth2.disconnect();
	}
</script>

</head>
<body>
	<div id="wrap" class="wrap_bg_01">
		<div class="loginLogo">
			<h2>
				<a href="login">STTM</a>
			</h2>
		</div>
		<div class="registerBtn">
			<ul>
				<li><span>아직 계정이 없나요?</span> <a href="register">SignUp</a></li>
			</ul>
		</div>

		<div class="loginWrap">
			<form action="" method="post" id="loginForm">
				<div class="inputField">
					<ul>
						<li><label for="userId">USER EMAIL</label> 
						<input type="text" id="userId" name="user_email" placeholder="Please enter your Email." value="galbi@naver.com">
							<!-- <input type="text" id="userId" name="user_email" placeholder="Please enter your Email." value="admin"> -->
						</li>
						<li><label for="userId">PASSWORD</label>
							<input type="password" id="pass" name="user_pass" placeholder="Please enter your Password." value="galbi1234">
							<!-- <input type="password" id="pass" name="user_pass" placeholder="Please enter your Password." value="0000"> -->
						</li>
						<li>
							<button type="submit" id="btnLogin">Login</button>
						</li>
						<!-- 구글 로그인 버튼 -->
						<li>
							<div class="g-signin2" data-onsuccess="onSignIn"></div>
						</li>
						<!-- 구글 로그인 버튼 -->
						<li class="forgot"><a href="resetPassword">Forgot your Password?</a></li>
					</ul>
				</div>
			</form>
		</div>
	</div>

	<!--  -->
	<script src="https://apis.google.com/js/platform.js?onload=init" async
		defer></script>
</body>



</html>