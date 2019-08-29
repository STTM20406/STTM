<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
    
    function admUpdateUser() {
		
    	// 아이디,패스워드 정규식
    	var re = /^[a-zA-Z0-9]{4,12}$/ 
    	
    	// 이메일  정규식
    	var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		
    	// 핸드폰 번호 정규식
    	var re3 = /^\d{3}-\d{4}-\d{4}$/;
    	
    	// 이름 정규식
    	var re4 = /^[가-힣]{2,4}$/;
    	
    	var user_email = document.getElementById("user_email");
    	var user_nm = document.getElementById("user_nm");
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
		
		if(!check(re4, user_nm, "이름은 이름은 한글 2~4자 이내로 입력해주세요.")) {
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
    	
    	$("#memViewForm").submit();
		alert("[관리자] 회원정보가 업데이트 되었습니다.");
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
    
    $(function() {
    	$("#user_st").val("${user_st}");
    });
</script>  
    
<section class="contents">

	<div id="tab-1" class="tab-content current">
		
		<form action="/admUpdateUser" method="post" id="memViewForm" name="join">	
			<table class="tb_style_01">
				<ul>
					<li>
						<label for="userEmail">이메일</label>
						<input type="text" id="user_email" name="user_email" 
							   value="${user_email}" placeholder="ex) sttm@ddit.com" readonly>
					</li>
					<li>
						<label for="userName">이름</label>
						<input type="text" id="user_nm" name="user_nm" 
						value="${user_nm}" placeholder="2-4자 이내의 한글">
					</li>
					<li>
						<label for="userPh">핸드폰번호</label>
						<input type="text" id="user_hp" name="user_hp" 
						value="${user_hp}" placeholder="ex) 010-0000-0000">
					</li>
					<li>
						<label for="userSt">회원 상태</label>
						<select id="user_st" name="user_st">
							<option value="N">정상</option>
							<option value="D">휴면계정</option>
						</select>
					</li>
				</ul>
			</table>
		</form>	
			
		<input type="button" id="btnMemUpdate" onclick="admUpdateUser()" class="inp_style_01" value="업데이트">
		
	</div>

</section>