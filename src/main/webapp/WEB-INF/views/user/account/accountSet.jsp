<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>

	/* 탭 설정 스타일 */
	.inquiryTr:hover{
			cursor: pointer;
	}
	ul.tabs {
		margin: 0px;
		padding: 0px;
		list-style: none;
	}
	
	ul.tabs li {
		background: none;
		color: #222;
		display: inline-block;
		padding: 10px 15px;
		cursor: pointer;
	}
	
	ul.tabs li.current {
		color: #222;
	}
	
	.tab-content {
		display: none;
		padding: 15px;
	}
	
	.tab-content.current {
		display: inherit;
	}

	/* 모달 설정 스타일 */
	.layer {display:none; position:fixed; _position:absolute; top:0; left:0; width:100%; height:100%; z-index:100;}
		.layer .bg {position:absolute; top:0; left:0; width:100%; height:100%; background:#000; opacity:.5; filter:alpha(opacity=50);}
		.layer .pop-layer {display:block;}
	
	.pop-layer {display:none; position: absolute; top: 50%; left: 50%; width: 410px; height:auto;  background-color:#fff; border: 5px solid #3571B5; z-index: 10;}	
	.pop-layer .pop-container {padding: 20px 25px;}
	.pop-layer p.ctxt {color: #666; line-height: 25px;}
	.pop-layer .btn-r {width: 100%; margin:10px 0 20px; padding-top: 10px; border-top: 1px solid #DDD; text-align:right;}
	
	a.cbtn {display:inline-block; height:25px; padding:0 14px 0; border:1px solid #304a8a; background-color:#3f5a9d; font-size:13px; color:#fff; line-height:25px;}	
	a.cbtn:hover {border: 1px solid #091940; background-color:#1f326a; color:#fff;}

</style>

<script>
	
	$(document).ready(function(){
		
		// ------- 슬라이드 토글 기능 -------
		
		$("#passForm").slideUp(0);
		$("#noticeForm").slideUp(0);
		$("#userStatusForm").slideUp(0);

		$("#resetPass").click(function () {
			$("#passForm").slideDown("slow");
			$("#noticeForm").slideUp("slow");
			$("#userStatusForm").slideUp("slow");
		});
		
		$("#setNotification").click(function () {
			$("#passForm").slideUp("slow");
			$("#noticeForm").slideDown("slow");
			$("#userStatusForm").slideUp("slow");
		});
		
		$("#setUserStatus").click(function () {
			$("#passForm").slideUp("slow");
			$("#noticeForm").slideUp("slow");
			$("#userStatusForm").slideDown("slow");
		});
		
		// ------- 탭 설정 -------
		$('ul.tabs li').click(function() {
			var tab_id = $(this).attr('data-tab');

			$('ul.tabs li').removeClass('current');
			$('.tab-content').removeClass('current');

			$(this).addClass('current');
			$("#" + tab_id).addClass('current');
		});

	});

	function funLoad(){
       var Cheight = $(window).height();
       $('#wrap').css({'height':Cheight+'px'});
   }
   window.onload = funLoad;
   window.onresize = funLoad;

   // ------- 비밀번호 재설정 -------
   function setPass(){
   	
	   	// 아이디,패스워드 정규식
	   	var re = /^[a-zA-Z0-9]{4,12}$/ 
	   	var user_pass = document.getElementById("user_pass");
	   	
		// 새 비밀번호 공백 일 경우
		if(passForm.user_pass1.value=="") {
		    alert("새 비밀번호를 입력해 주세요");
		    passForm.user_pass1.focus();
		    return false;
		}else if(passForm.user_pass.value==""){
			 alert("새 비밀번호를 다시 입력하여 확인해 주세요");
				 passForm.user_pass2.focus();
				 return false;
		}
		
		// ------- 패스워드가 정규식에 안맞을 경우 -------
		if(!check(re, user_pass1, "패스워드는 4~12자의 영문 대소문자와 숫자로만 입력")) {
		    return false;
		} else if(!check(re, user_pass, "패스워드는 4~12자의 영문 대소문자와 숫자로만 입력")){
			return false;
		}
		
		$("#passForm").submit();
		
		alert("비밀번호가 업데이트 되었습니다!");
   }
   
   // ------- 알림 설정 업데이트 -------
	function setNotice() {
		$("#noticeForm").submit();
		alert("회원님의 알림 설정이 업데이트 되었습니다.");			
	}

   // ------- 휴면계정 전환 버튼() -------
   function inactiveUser() {
		$("#userStatusForm").submit();
		alert("회원님의 계정이 휴면 상태로 전환 되었습니다.");			
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
    
    // ------- 일반 사용자 프로필 업데이트 -------
    function setProfile(){
		$("#profileForm").submit();
		alert("회원님의 프로필이 업데이트 되었습니다.");			
    }
    
    // ------- 모달 설정 스크립트 -------
    function layer_open(el){

		var temp = $('#' + el);
		var bg = temp.prev().hasClass('bg');	//dimmed 레이어를 감지하기 위한 boolean 변수

		if(bg){
			$('.layer').fadeIn();	//'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다. 
		}else{
			temp.fadeIn();
		}

		//  -------화면의 중앙에 레이어를 띄운다. -------
		if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
		else temp.css('top', '0px');
		if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
		else temp.css('left', '0px');

		temp.find('a.cbtn').click(function(e){
			if(bg){
				$('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다. 
			}else{
				temp.fadeOut();
			}
			e.preventDefault();
		});

		$('.layer .bg').click(function(e){	//배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
			$('.layer').fadeOut();
			e.preventDefault();
		});

	}				
    
</script>

<section class = "contents">	

<div id="container">

	<div class="sub_menu">
			<ul class="tabs">
				<li data-tab="tab-1">설정3</li>
				<li data-tab="tab-2">프로필3</li>
			</ul>
	</div>
	
	<div class="tab_con">
			
		<div id="tab-1" class="tab-content current">
		
			<div id="setAccount" >
			
				<!-- 비밀번호 설정 -->
				<div id="resetPass" class="loginWrap" style="background-color:cornsilk" onclick="setUserPass"><label>비밀번호</label>
					<form action="/setUserPass" method="post" id="passForm">
						<div class="inputField">
							<ul>
								<li>
									<label for="user_pass1">새 비밀번호</label>
									<input type="text" id="user_pass1" name="user_pass1" 
										placeholder="패스워드는 4~12자의 영문 대소문자와 숫자로만 입력" value="${user_pass}">
								</li>
								<li>
									<label for="user_pass2">새 비밀번호 확인</label>
									<input type="text" id="user_pass" name="user_pass" 
										placeholder="패스워드는 4~12자의 영문 대소문자와 숫자로만 입력" value="${user_pass}">
								</li>
								<li>
									<input type="button" class="btn_style_01" onclick="setPass()" value="비밀번호 업데이트">
								</li>
							</ul>
						</div>
					</form>
				</div>
			
				<br><br>
			
				<!-- 알림 설정 -->   
				<div id="setNotification" class="loginWrap" style="background-color:lightsalmon"><label>알림설정</label><br>
					<form action="/setUserNotice" method="post" id="noticeForm">
						<div class="inputField">
							<ul>
								<!-- 프로젝트에 대한 알림 -->
								<li>
									<br><br>
									<input type="checkbox" id="notice" name="project" value="${not_cd}"> 프로젝트에 대한 알림<br>
								</li>
								<li>
									<input type="checkbox" id="notice" name="chat" value="${not_cd}"> 채팅 메세지 알림<br>
								</li>
								<li>
									<input type="checkbox" id="notice" name="inquiry" value="${not_cd}"> 1:1문의 답변 알림<br>
								</li>
								<li>
									<input type="checkbox" id="notice" name="work" value="${not_cd}"> 업무에 대한 알림<br>
								</li>
								<li>
									<input type="button" onclick="setNotice()" value="알림 설정 업데이트">
								</li>
							</ul>
						</div>
					</form>
				</div>
				
				<br><br>
			
				<!-- 휴면 계정 설정 -->  
				<div id="setUserStatus" class="loginWrap" style="background-color:paleturquoise" onclick="setUserStatus"><label>휴면계정 전환</label>
					<form action="/setUserStatus" method="post" id="userStatusForm">
						<div class="inputField">
							<a href="#" class="btn-example" onclick="layer_open('layer2');return false;">
								<button type="button" id="btnPjrMemList">휴면 계정 전환</button>
							</a>
							
							<div class="layer">
								<div class="bg"></div>
								<div id="layer2" class="pop-layer">
									<div class="pop-container">
										<div class="pop-conts">
											
											<!--content //--><!--content //--><!--content //--><!--content //--><!--content //--><!--content //--><!--content //--><!--content //-->
											
											<table class="tb_style_01">
													<tbody>
														<tr>
														
															<th>프로젝트 멤버 리스트</th>
															
															<select>
																<option>

																	<c:forEach items="${getMyPrjMemList}" var="myPrMemList">
																		<tr class="prjMemListTr" data-prj_id="${myPrMemList.prj_id}">
																			<td class="prj_id">${myPrMemList.prj_id}</td>
																		</tr>
																	</c:forEach>
																
																</option>
																
															</select>
															
															
														</tr>
													</tbody>
												</table>
													
												<input type="button" onclick="inactiveUser()" value="소유권 이전 버튼" >
												<div class="btn-r">
													<a href="#" class="cbtn">Close</a>
												</div>
												<!--// content-->
												
											<!--content //--><!--content //--><!--content //--><!--content //--><!--content //--><!--content //--><!--content //--><!--content //-->
										</div>
									</div>
								</div>
							</div>	
							
						</div>
					</form>
				</div>
			</div> 
		
		</div>
		
		<div id="tab-2" class="tab-content">
			<div id="setProfile" >
				<!-- 프로필 설정 -->
				<div id="setProfile" class="loginWrap" style="background-color:cornsilk"><label>비밀번호</label>
					<form action="/setUserPass" method="post" id="profileForm">
						<div class="inputField">
							<ul>
							
								<li>
									<input type="hidden" id="user_email" name="user_email" value="${user_email}">
								</li>

								<li>
									<input type="hidden" id="user_pass" name="user_pass" value="${user_pass}">
								</li>

								<li>
									<label for="user_nm">이름</label>
									<input type="text" id="user_nm" name="user_nm" 
										placeholder="" value="${user_nm}">
								</li>
								
								<li>
									<label for="user_hp">전화번호</label>
									<input type="text" id="user_hp" name="user_hp" 
										placeholder="010-0000-0000" value="${user_hp}">
								</li>
								
								<li>
									<input type="button" onclick="setProfile()" value="프로필 업데이트">
								</li>
								
							</ul>
						</div>
					</form>
				</div>
			</div>
		</div>
		
	</div>
	
</div>
	
</section>
