<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    이 화면은 관리자가 회원의 정보를 수정하는 페이지 입니다.
<script>

    function admUpdateUser() {
		$("#memViewForm").submit();
		alert("[관리자] 회원정보가 업데이트 되었습니다.");
	}
    
</script>  
    
<section class="contents">

	<div id="tab-1" class="tab-content current">
		
		<form action="/admUpdateUser" method="post" id="memViewForm">	
			<table class="tb_style_01">
				<ul>
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
						<label for="userPh">핸드폰번호</label>
						<input type="text" id="user_hp" name="user_hp" 
						value="${userVo.user_hp}" placeholder="ex) 010-0000-0000">
					</li>
					<li>
						<label for="userSt">회원 상태</label>
						<input type="text" id="user_st" name="user_st" 
						value="${userVo.user_st}">
					</li>
				</ul>
			</table>
		</form>	
			
		<input type="button" id="btnMemUpdate" onclick="admUpdateUser()" value="업데이트">
		
	</div>

</section>