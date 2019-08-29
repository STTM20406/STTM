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
		
		// 휴면 계정 전환 레이어창
		$('.inactiveUser').on("click", function(){
		        var $href = $(this).attr('href');
		        layer_popup($href);
		});
		
		// 소유권 이전 버튼 클릭시 이벤트 
		$(".transOwn").on("click","#subOwership", function(){
			var user_email = $(this).parent(".transOwn").attr("id");
			console.log(user_email);

 			var user_email2 = $(this).siblings("#transPrjId").val();
			console.log(user_email2);
			
			$("#transferOwership").val(user_email);
			$("#transferPrjId").val(user_email2);
			
			$("#ownEmailForm").submit();
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
    
    // ------- 일반 사용자 프로필 업데이트 -------
    function setProfile(){
    	
    	// 핸드폰 번호 정규식
    	var re3 = /^\d{3}-\d{4}-\d{4}$/;
    	
    	// 이름 정규식
    	var re4 = /^[가-힣]{2,4}$/;
    	
    	var user_nm = document.getElementById("user_nm");
    	var user_hp = document.getElementById("user_hp");
    	
    	// 이름
		if(profileForm.user_nm.value=="") {
		    alert("이름을 입력해 주세요");
		    join.user_nm.focus();
		    return false;
		}
		
		if(!check(re4, user_nm, "이름은 이름은 한글 2~4자 이내로 입력해주세요.")) {
			return false;
		}
		
		// 핸드폰 번호
		if(profileForm.user_hp.value=="") {
		    alert("핸드폰 번호를 입력해 주세요");
		    join.user_hp.focus();
		    return false;
		}
		
		if(!check(re3, user_hp, "010-0000-0000 형식에 맞는 핸드폰 번호를 입력해주세요.")) {
		    return false;
		}
    	
		$("#profileForm").submit();
		alert("회원님의 프로필이 업데이트 되었습니다.");			
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
    
  //------- 모달 설정 스크립트 -------
  //layer popup - 프로젝트 생성
  	function layer_popup(el){
  		console.log(el);

          var $el = $(el);		//레이어의 id를 $el 변수에 저장
          var isDim = $el.prev().hasClass('dimBg');	//dimmed 레이어를 감지하기 위한 boolean 변수

          isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

          var $elWidth = ~~($el.outerWidth()),
              $elHeight = ~~($el.outerHeight()),
              docWidth = $(document).width(),
              docHeight = $(document).height();

          // 화면의 중앙에 레이어를 띄운다.
          if ($elHeight < docHeight || $elWidth < docWidth) {
              $el.css({
                  marginTop: -$elHeight /2,
                  marginLeft: -$elWidth/2
              })
          } else {
              $el.css({top: 0, left: 0});
          }

          $el.find('a.btn-layerClose').click(function(){
              isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
              return false;
          });

          $('.layer .dimBg').click(function(){
              $('.dim-layer').fadeOut();
              return false;
          });

      }				
    
</script>

<section class = "contents">	

<div id="container">

	<div class="sub_menu">
			<ul class="tabs">
				<li data-tab="tab-1">
				설정
				</li>
				<li data-tab="tab-2">프로필</li>
			</ul>
	</div>
	
	<div class="tab_con">
			
		<div id="tab-2" class="tab-content">
			<div id="setProfile" >
				<!-- 프로필 설정 -->
				<div id="setProfile" class="loginWrap" style="background-color:cornsilk"><label>비밀번호</label>
					<form action="/setUserPass" method="post" id="profileForm">
						<div class="inputField">
							<ul>
							
								<li>
									<label for="user_nm">이름</label>
									<input type="text" id="user_nm" name="user_nm" 
										placeholder="5자(성은 제외)이내" value="${user_nm}">
								</li>
								
								<li>
									<label for="user_hp">전화번호</label>
									<input type="text" id="user_hp" name="user_hp" 
										placeholder="010-0000-0000" value="${user_hp}">
								</li>
								
								<li>
									<input type="button" onclick="setProfile()" class="btn_style_01" value="프로필 수정">
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
