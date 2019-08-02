<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    이 화면은 관리자가 회원의 정보를 수정하는 페이지 입니다.
    
<section class="contents">

	<div id="tab-1" class="tab-content current">
		
		<form action="/admUpdateUser" method="post" id="memViewForm">	
			<table class="tb_style_01">
				<ul>
					<li>
						<label for="userEmail">이메일</label>
						<input type="text" id="user_email" name="user_email" 
							   value="${user_email}" placeholder="ex) sttm@ddit.com">
					</li>
					<li>
						<label for="userEmail">이메일</label>
						<input type="text" id="user_email" name="user_email" 
							   value="${userVo.user_email}" placeholder="ex) sttm@ddit.com">
					</li>
					<li>
						<label for="userName">이름</label>
						<input type="text" id="user_nm" name="user_nm" 
						value="${userVo.user_nm}" placeholder="5자(성은 제외)이내">
					</li>
					<li>
						<label for="userPass">비밀번호</label>
						<input type="password" id="user_pass" name="user_pass" 
						value="${userVo.user_pass}" placeholder="4~12자의 영문 대소문자와 숫자">
					</li>
					<li>
						<label for="userPh">핸드폰번호</label>
						<input type="text" id="user_hp" name="user_hp" 
						value="${userVo.user_hp}" placeholder="ex) 010-0000-0000">
					</li>
				</ul>
			</table>
		</form>	
			
		<input type="button" id="btnMemUpdate" onclick="admUpdateUser()" value="업데이트">
		
	</div>

</section>