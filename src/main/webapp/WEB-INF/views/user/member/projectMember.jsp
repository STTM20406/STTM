<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- basic Library --%>
<%@include file="/WEB-INF/views/common/baseLib.jsp"%>

<style>
	
	.userTr:hover{
		cursor: pointer;
	}
	
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
	
	.logout {
		color: #e1e1e1;
	}
	
	.logon {
		color: #0ceb47;
	}
</style>

<script>
$(document).ready(function(){
	
// 	프로젝트 멤버 리스트중 한개의 멤버를 클릭시
// 	$(".prjMemTr").on("click", function(){
// 		var user_email = $(this).find(".user_email").attr("id");
// 		$('#user_email').val(user_email);
// 		$('#prjMemView').submit();
// 	});
	
	socket.onmessage = function(event) {
      console.log("ReceiveMessage: ", event.data + "\n");
      var data = event.data;
      if(!data.startsWith("lst:")) {
	      var $socketAlert = $('#socketAlert p');
	      $socketAlert.text(event.data);
	      $(".socketAlram").fadeIn(300);
	      $(".socketAlram").animate({right:"0px"}, 500);
	      setTimeout(function(){
	         $(".socketAlram").fadeOut(300);
	         $(".socketAlram").animate({right:"-350px"}, 500);
	         
	      },3000);
      } else {
    	  var ids = data.split("lst:")[1].split(",");
    	  console.log(ids);
//     	  var prjTable = document.getElementById("prjMemTable");
    	  var fndTable = document.getElementById("friendTable");
    	  if(fndTable) {
//     		  var prjTr = $(prjTable).find("tr");
    		  var fndTr = $(fndTable).find(".userTr");
    		  ids.forEach(function(id) {
//     			  $(prjTr).each(function(){
//     				 if($(this).data("user_email") == id) {
//     					 $(this).find("span").prop("class", "logon");
//     				  }
//     			  });
    			  $(fndTr).each(function() {
    				 if($(this).data("user_email") == id) {
    					 $(this).find("span").prop("class", "logon");
    				 }
    			  });
    		  });
    	  } else {
    		  console.log("테이블 없음");
    	  }
      }
   };
		
	// 프로젝트 멤버 tr클릭시 레이어창 띄우기
	$('.prjMember').on("click", function(){
	        var $href = $(this).attr('href');
	        layer_popup($href);
	});

	// 요청 받은 친구 목록 클릭시
	$('.requestedFriendsList').on("click", function(){
	        var $href = $(this).attr('href');
	        layer_popup($href);
	});
	
	// 친구 요청 버튼 클릭시
	$('.friendsBtn').on("click", function(){
	        var $href = $(this).attr('href');
	        layer_popup($href);
	});

	// 친구 요청 a태그 클릭시
	$('.prjMemTr').on("click","#friendReqAtag", function(){
		alert("친구 요청이 전송 되었습니다.");	
	});

	// 친구 수락 버튼 클릭시
	$('#btnAcceptReq').on("click", function(){
		
		var acceptEmail = $(this).find("#acceptEmail").text();
		$("#acceptEmail").val(user_email);
		
		$('#friendsRequestListForm').submit();
	});
	
	// 친구 거절 버튼 클릭시
	$('#btnDenyReq').on("click", function(){
		$('#friendsRequestListForm').submit();
	});
	
	// ------- 탭 설정 -------
	$('ul.tabs li').click(function() {
		var tab_id = $(this).attr('data-tab');

		$('ul.tabs li').removeClass('current');
		$('.tab-content').removeClass('current');

		$(this).addClass('current');
		$("#" + tab_id).addClass('current');
		var tabname = $(this).data("tab");
		console.log(tabname);
		if(socket.readyState==1) {
			if(tabname == "tab-2") {
				var prjTable = document.getElementById("prjMemTable");
				$(prjTable).find("span").prop("class","logout");
				socket.send("fnd,${USER_INFO.user_email}");
			} else if (tabname == "tab-1") {
		    	var fndTable = document.getElementById("friendTable");
				$(fndTable).find("span").prop("class","logout");
				socket.send("prjMem,${USER_INFO.user_email}");								
			}
		}
		
		
	});
	
	// ------- 설정 버튼 -------
// 	$(".user_set_list").hide();
// 	$(".icon_set").on("click", function(){
// 		$(".user_set_list").fadeIn();
// 	});
// 	$(".user_set_list").on("mouseleave", function(){
// 		$(".user_set_list").fadeOut();
// 	});
	
});	

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

// 친구 추가 버튼
function requestFriends() {
	$("#friendsRequestForm").submit();
	alert("친구 요청이 완료 되었습니다.");
}

// 요청 받은 친구 목록 클릭시 
function requestedFriendsList() {
	$("#friendsRequestListForm").submit();
	alert("요청 받은 친구 목록입니다. 나중에 삭제 ");
}

// function projectMemListAjax(){
// 	$.ajax({
// 		url: "/projectMemberList",
// 		method: "get",
// 		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
// // 		PageVo pageVo, Model model, HttpSession session
// 		data: 
// 		success: function(data){
			
// 		}
		
// 	});
// }

</script>

<section class="contents">

<div id="container">

	<div class="sub_menu">
			<ul class="tabs">
				<li data-tab="tab-1">
					<a href="/projectMember">프로젝트 멤버</a>
				</li>
				<li data-tab="tab-2">
					<a href="/friendsList">친구 리스트</a>
				</li>
			</ul>
	</div>

	<div class="tab_con">
	
		<div id="tab-1" class="tab-content current">
			
			<!-- 프로젝트 리스트 가져오기 시작 -->
			<div class="project_wrap">
				<div class="project_list my_project_list">
					<c:forEach items="${projectList}" var="projectList">
						<div class="project_item" id="${projectList.prj_id}">
							<ul class="project_item_hd">
								<li class="prj_title" id="${projectList.prj_id}">
									<a href="/projectMemberList?memPrjId=${projectList.prj_id}">${projectList.prj_nm}</a>
								</li>
							</ul>
						</div>
					</c:forEach>
				</div>
			</div>
			<!-- 프로젝트 리스트 가져오기 시작 -->
			
			<form id="prjMemView" action="/projectMemView" method="get">
				<input type="hidden" id="user_email" name="user_email">
			</form>
			
		</div>
		
		
	</div>
	
</div>

</section>