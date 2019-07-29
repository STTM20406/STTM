<!doctype html>
<html>
<head>
  <title>WEB1 - Welcome</title>
  <meta charset="utf-8">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="colors.js"></script>
  
  <!-- 인증과 관련된 처리를 해주는 코드 -->
  <script src="https://apis.google.com/js/platform.js" async defer></script>
  <script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
  
  <!-- content에 클라이언트 아이디 입력 -->
  <meta name="google-signin-client_id" content="490177422077-s1so09h9cmn4sg1cek2d847d8vekv7kt.apps.googleusercontent.com">
  
  <script>
    function onSignIn(googleUser) {
      var profile = googleUser.getBasicProfile();
      console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
      console.log('Name: ' + profile.getName());
      console.log('Image URL: ' + profile.getImageUrl());
      console.log('Email: ' + profile.getEmail()); 
    }
  </script>
</head>
<body>
  <!-- 구글 로그인 버튼 추가 -->
  <!-- data-oncuccess =  -->
  <div class="g-signin2" data-onsuccess="onSignIn"></div>

  <h1><a href="index.html">WEB</a></h1>
  <input id="night_day" type="button" value="night" onclick="
    nightDayHandler(this);
  ">
  <ol>
    <li><a href="1.html">HTML</a></li>
    <li><a href="2.html">CSS</a></li>
    <li><a href="3.html">JavaScript</a></li>
  </ol>
  <h2>WEB</h2>
  <p>The World Wide Web (abbreviated WWW or the Web) is an information space where documents and other web resources are 
  identified by Uniform Resource Locators (URLs), interlinked by hypertext links, and can be accessed via the Internet.[1] 
  English scientist Tim Berners-Lee invented the World Wide Web in 1989. He wrote the first web browser computer program in 
  1990 while employed at CERN in Switzerland.[2][3] The Web browser was released outside of CERN in 1991, first to other 
  research institutions starting in January 1991 and to the general public on the Internet in August 1991.
  </p>
</body>
</html>