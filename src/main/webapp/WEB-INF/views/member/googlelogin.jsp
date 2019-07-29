<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="${cp }/js/js.cookie.js"></script>
	<!-- 구글 로그인 API 시작 -->
	<meta name="google-signin-scope" content="profile email">
	<!-- Client ID : Google에 신청해 발급받은 ID를 content의 값으로 입력 -->
    <meta name="google-signin-client_id" content="1007667076011-rnn1ucbiirf2uqshgan4a62rjthqigbk.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <!-- 구글 로그인 API 끝 -->
    <title>Sign in</title>
	<style>
		.abcRioButton abcRioButtonLightBlue { margin: 0 auto; }
	</style>
    <!-- Bootstrap core CSS -->
    <link href="${cp }/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${cp }/css/signin.css" rel="stylesheet">
	<script>
		$(document).ready(function() {
			//문서 로딩이 완료되고 나서 실행되는 부분
			//rememberMe CheckBox 값 체크
			//1. rememberMe 쿠키가 있는지?
			//	1.1. 있다면, 그 값이 true인지?
			//	1.2. true라면, input id = "rememberMe" CheckBox를 체크.
			var rememberMe = Cookies.get("rememberMe");
			if(rememberMe=="true"){
				$("#rememberMe").prop("checked", true);
				$("#userId").val(Cookies.get("userId"));
				$("#password").focus();
			} else {
				$("#rememberMe").prop("checked", false);
				$("#userId").focus();
			}
			
			$("#frm").on("submit", function() {

			})
			
			$("#password").keypress(function(key) {
				if(key.which == 13) {
					$("#signInBtn").click();
				}
			})
		})
	</script>
  </head>

  <body>

    <div class="container">
		
      <form id = "frm" class="form-signin" action="${cp }/login" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="userId" class="sr-only">User ID</label>
        <input type="text" id="userId" name = "userId" class="form-control" placeholder="User ID" required>
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" class="form-control" name="password" placeholder="Password" required>
        <div class="checkbox">
          <label>
            <input type="checkbox" id = "rememberMe" name = "rememberMe" value="true"> Remember me
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" id = "signInBtn">Sign in</button>
      </form>
        <!-- 구글 로그인 버튼 시작 -->
        <div class="g-signin2" data-onsuccess="onSignIn"></div><br>
        <!-- 구글 로그인 버튼 끝 -->
      <form id="googleForm" action="/googleLogin" method="post">
      	<input type="hidden" id="googleEmail" name="googleEmail"/>
      	<input type="hidden" id="googleToken" name="googleToken"/>
      </form>
        <!-- 구글 로그아웃 링크 시작 -->
        <a href="#" onclick="signOut();">Sign out</a>
        <!-- 구글 로그아웃 링크 끝 -->
        
        <!-- 로그아웃 링크를 선택했을 때 실행할 함수 시작 -->
        <!-- 이 로직은 로그인 화면이 아닌 나머지 화면에서 로그아웃 요청때 실행 예정 -->
	<script>
	  function signOut() {
	    var auth2 = gapi.auth2.getAuthInstance();
	    auth2.signOut().then(function () {
	      console.log('User signed out.');
	    });
	  }
	</script>
        <!-- 로그아웃 링크를 선택했을 때 실행할 함수 끝 -->
      
    </div> <!-- /container -->
    
    <!-- 
    	!! 아직 로그인 정보를 서버로 보내 메인으로 넘어가는 처리는 하지 않았음 !!
     -->
    
    <!-- 구글 로그인 버튼을 선택했을때 실행할 함수 시작 -->
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
    <!-- 구글 로그인 버튼을 선택했을때 실행할 함수 끝 -->
    </script>
  </body>
</html>
