<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

관리자가 회원을 등록하는 페이지 입니다.

<style>
	.userTr:hover{
		cursor: pointer;
	}
</style>

<script>
		
	function funLoad(){
        var Cheight = $(window).height();
        $('#wrap').css({'height':Cheight+'px'});
    }
    window.onload = funLoad;
    window.onresize = funLoad;
	
    // 회원가입 정규식
    function validate(){
    	
    	// 아이디,패스워드 정규식
    	var re = /^[a-zA-Z0-9]{4,12}$/ 
    	
    	// 이메일  정규식
    	var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		
    	// 핸드폰 번호 정규식
    	var re3 = /^\d{3}-\d{4}-\d{4}$/;
    	
    	var user_email = document.getElementById("user_email");
    	var user_nm = document.getElementById("user_nm");
    	var user_pass = document.getElementById("user_pass");
    	var user_hp = document.getElementById("user_hp");
    	
    	// 이메일
		if(join.user_email.value=="") {
		    alert("이메일를 입력해 주세요");
		    join.user_email.focus();
		    return false;
		}

		if(!check(re2, user_email, "적합하지 않은 이메일 형식입니다.")) {
			return false;
		}
		
		// 이름
		if(join.user_nm.value=="") {
		    alert("이름을 입력해 주세요");
		    join.user_nm.focus();
		    return false;
		}
		
		// 비밀번호
		if(join.user_pass.value=="") {
		    alert("비밀번호를 입력해 주세요");
		    join.user_pass.focus();
		    return false;
		}

		if(!check(re, user_pass, "패스워드는 4~12자의 영문 대소문자와 숫자로만 입력")) {
		    return false;
		}
		
		// 핸드폰 번호
		if(join.user_hp.value=="") {
		    alert("핸드폰 번호를 입력해 주세요");
		    join.user_hp.focus();
		    return false;
		}
		
		if(!check(re3, user_hp, "010-0000-0000 형식에 맞는 핸드폰 번호를 입력해주세요.")) {
		    return false;
		}
		
		$("#registerForm").submit();
		
		alert("회원가입이 완료되었습니다.");
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

<section class="contents">

	<div id="tab-1" class="tab-content current">
		
		<form action="" method="post" id="registerForm" name="join">
				<div class="inputField">
					<ul>
						<li>
							<label for="userEmail">이메일</label>
							<input type="text" id="user_email" name="user_email" 
								   value="${user_email}" placeholder="ex) sttm@ddit.com">
						</li>
						<li>
							<label for="userName">이름</label>
							<input type="text" id="user_nm" name="user_nm" placeholder="5자(성은 제외)이내">
						</li>
						<li>
							<label for="userPass">비밀번호</label>
							<input type="password" id="user_pass" name="user_pass" 
							 placeholder="4~12자의 영문 대소문자와 숫자">
						</li>
						<li>
							<label for="userPh">핸드폰번호</label>
							<input type="text" id="user_hp" name="user_hp" placeholder="ex) 010-0000-0000">
						</li>
						<li>
							<input type="button" id="joinBtn" onclick="validate()" value="가입하기">
						</li>
					</ul>
				</div>
			</form>
		
	</div>

</section>