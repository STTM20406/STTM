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
	
	
	// 프로젝트 멤버 리스트중 한개의 멤버를 클릭시
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
    	  var prjTable = document.getElementById("prjMemTable");
    	  var fndTable = document.getElementById("friendTable");
    	  if(prjTable && fndTable) {
    		  var prjTr = $(prjTable).find("tr");
    		  var fndTr = $(fndTable).find(".userTr");
    		  ids.forEach(function(id) {
    			  $(prjTr).each(function(){
    				 if($(this).data("user_email") == id) {
    					 $(this).find("span").prop("class", "logon");
    				  }
    			  });
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
   	socket.onopen = function(event) {
		socket.send("prjMem,${USER_INFO.user_email}");
   	}
		
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
				<li data-tab="tab-1">프로젝트 멤버</li>
				<li data-tab="tab-2">친구 리스트</li>
			</ul>
	</div>

	<div class="tab_con">
	
		<div id="tab-1" class="tab-content current">
			
			<form id="prjMemView" action="/projectMemView" method="get">
				<input type="hidden" id="user_email" name="user_email">
			</form>
			
			<div>
				<table class="tb_style_01" id="prjMemTable">
					<colgroup>
						<col width="30%">
						<col width="30%">
						<col width="30%">
					</colgroup>
					<tbody>
						<tr>
						
							<th>사용자 이메일</th>
							<th>사용자 이름</th>
							<th></th>
		
							<c:forEach items="${projectMemList}" var="prjVo">
							
								<tr class="prjMemTr" data-user_email="${prjVo.user_email}">
									<td class="user_email" id="${prjVo.user_email}">
<%-- 										<a href="#layer2" class="trPrjMemAdd">${prjVo.user_email}</a> --%>
										<a href="#layer2" class="prjMember">${prjVo.user_email}</a><span id="log_${prjVo.user_email }" class="logout"> ●</span>
									</td>
									
									<td>${prjVo.user_nm}</td>
									<td>
										<a href="/projectMemberList?frdRequEmail=${prjVo.user_email}" id="friendReqAtag" class="inp_style_01">친구요청</a>
									</td>
									
								</tr>
								
							</c:forEach>
								
						</tr>
					</tbody>
				</table>
				
				 <div class="col-lg-12" id="ex1_Result2" ></div> 

			</div>
			
			<div class="pagination">
					<c:choose>
						<c:when test="${pageVo.page == 1 }">
							<a class="btn_first"></a>
						</c:when>
						<c:otherwise>
							<a href="${cp}/projectMemberList?page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
						
						</c:otherwise>
					</c:choose>
		
					<c:forEach begin="1" end="${paginationSize}" var="i">
						<c:choose>
							<c:when test="${pageVo.page == i}">
								<span>${i}</span>
							</c:when>
							<c:otherwise>
							<a href="${cp}/projectMemberList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
							</c:otherwise>
						</c:choose>
		
					</c:forEach>
		
					<c:choose>
						<c:when test="${pageVo.page == paginationSize}">
							<a href class="btn_last"></a>
						</c:when>
						
						<c:otherwise>
							<a href="${cp}/projectMemberList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
						</c:otherwise>
					</c:choose>
			
			</div>
			
			<form action="/나중에 입력하기" id="prjMemTrForm" method="post">
				<div id="layer2" class="pop-layer">
				    <div class="pop-container">
				        <div class="pop-conts">
				            <!--content //-->
			
				            <p class="ctxt mb20">
							친구 등록
							</p>
			
							<input type="button" class="inp_style_01" id="" onclick="requestFriends()" value="친구 요청">
							
				            <div class="btn-r">
				                <a href="#" class="btn-layerClose">Close</a>
				            </div>
				            <!--// content-->
				        </div>
				    </div>
				</div>
			</form>
			
		</div>
		
		
		<div id="tab-2" class="tab-content">
			
<!-- 			<form id="frdEmailFrom" action="/projectMemberList" method="get"> -->
<%-- 				<input type="text" id="frd_email" name="frd_email" value="${friendsList.frd_email}"> --%>
<!-- 			</form> -->
			
			<div>
				<div class="searchBox">
					<div class="tb_sch_wr">
						<fieldset id="hd_sch">
						 	<form id="frmSearch" action="/friendsSearchList" method="get">
			<input type="hidden" id="inq_cate" name="inq_cate" value="INQ01"/>
								<input type="hidden" id="selectBoxText" name="selectBoxText" value="userEmail"/>
				                <legend>사이트 내 전체검색</legend>
					                <select id="search">
					                	<option value="userEmail">이메일</option>
					                </select>
				                <input type="text" name="keyword" id="keyword" maxlength="20" placeholder="검색어를 입력해주세요">
				                <button type="submit" id="sch_submit" value="검색">검색</button>
			                </form>
		            	</fieldset>
		           	</div>
	          	</div>
				
				<table class="tb_style_01" id="friendTable">
					<colgroup>
						<col width="10%">
						<col width="40%">
						<col width="30%">
						<col width="10%">
						<col width="10%">
					</colgroup>
					<tbody>
						<tr>
						
<!-- 							<th>사용자 이메일 - 추후 삭제</th> -->
							<th>친구 이름</th>
							<th>친구 이메일</th>
							<th>친구삭제</th>
		
							<c:forEach items="${friendsList}" var="prjVo">
							
								<tr class="userTr" data-user_email="${prjVo.frd_email}">
<%-- 									<td class="user_email">${prjVo.user_email}</td> --%>
									<td>${prjVo.user_nm}</td>
									<td>${prjVo.frd_email}<span class="logout"> ●</span></td>
									<td class="delFriends">
<%-- 										<a href="/deleteFriends?frd_email=${prjVo.frd_email}" class="frdDel">삭제하기</a> --%>
										<a href="/deleteFriends?frd_email=${prjVo.frd_email}" class="a_style_04">삭제하기</a>
<!-- 										<input type="button" id="btnDeleteFriends" class="btn_style_04" onclick="deleteFriends()" value="친구삭제"> -->
<%-- 										${prjVo.frd_email} --%>
									</td>
								</tr>
								
							</c:forEach>
							
						</tr>
					</tbody>
				</table>
			</div>
			
<!-- 			<input type="button" id="btnFriendsReqList" class="inp_style_04" onclick="layer_open('layer3');return false;" value="친구 요청 목록"> -->
<!-- 			<input type="button"  onclick="layer_open('layer4');return false;" value="친구 요청"> -->
			
			<a href="#layer3" class="requestedFriendsList a_style_01">요청 받은 친구 목록</a>
			<a href="#layer4" class="friendsBtn a_style_01">친구 요청</a>
			
			<div class="pagination">
					<c:choose>
						<c:when test="${pageVo.page == 1 }">
							<a href class="btn_first"></a>
						</c:when>
						<c:otherwise>
<%-- 							<a href="${cp}/friendsSearchList?page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a> --%>
							<a href="${cp}/projectMemberList?page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
						
						</c:otherwise>
					</c:choose>
		
					<c:forEach begin="1" end="${paginationSize}" var="i">
						<c:choose>
							<c:when test="${pageVo.page == i}">
								<span>${i}</span>
							</c:when>
							<c:otherwise>
<%-- 								<a href="${cp}/friendsSearchList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a> --%>
								<a href="${cp}/projectMemberList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
							</c:otherwise>
						</c:choose>
		
					</c:forEach>
		
					<c:choose>
						<c:when test="${pageVo.page == paginationSize}">
							<a href class="btn_last"></a>
						</c:when>
						
						<c:otherwise>
<%-- 							<a href="${cp}/friendsSearchList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a> --%>
							<a href="${cp}/projectMemberList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
						</c:otherwise>
					</c:choose>
			
			</div>
		
<!-- 			<form action="/friendsRequestList" id="friendsRequestListForm" method="get"> -->
			<form action="/projectMemberList" id="friendsRequestListForm" method="post">
				<div id="layer3" class="pop-layer">
				    <div class="pop-container">
				        <div class="pop-conts">
				            <!--content //-->
			
							<table class="tb_style_01">
								<colgroup>
									<col width="30%">
									<col width="30%">
									<col width="30%">
								</colgroup>
								<tbody>
									<tr>
									
										<th>친구 이름</th>
										<th>수락</th>
										<th>거절</th>
					
										<c:forEach items="${friendsRequestList}" var="friReqList">
										
											<tr class="friReqTr" data-user_email="${friReqList.user_email}">
												<td>${friReqList.user_nm}</td>
												<td>
<!-- 													<input id="btnAcceptReq" type="button" class="inp_style_01" value="수락"> -->
<%-- 													<input type="hidden" id="acceptEmail" value="${friReqList.user_email}"> --%>
													<a href="/projectMemberList?acceptEmail=${friReqList.user_email}" class="a_style_01">수락</a>
												</td>
												<td>
<!-- 													<input id="btnDenyReq" type="button" class="inp_style_04" value="거절"> -->
<%-- 													<input type="hidden" id="denyEmail" value="${friReqList.user_email}"> --%>
													<a href="/projectMemberList?denyEmail=${friReqList.user_email}" class="a_style_04">거절</a>
												</td>
											</tr>
											
										</c:forEach>
											
										<form>
											
										</form>
										
									</tr>
								</tbody>
							</table>
							
				            <div class="btn-r">
				                <a href="#" class="btn-layerClose">Close</a>
				            </div>
				            
				            <!--// content-->
				        </div>
				    </div>
				</div>
			</form>

			<form action="/friendsRquest" id="friendsRequestForm" method="post">
				<div id="layer4" class="pop-layer">
				    <div class="pop-container">
				        <div class="pop-conts">
				            <!--content //-->
			
							<input type="text" id="req_email" name="req_email" value="${req_email}"
							placeholder="친구 아이디를 입력 해주세요">
							<input type="button" class="inp_style_01" id="" onclick="requestFriends()" value="친구 요청">
							
				            <div class="btn-r">
				                <a href="#" class="btn-layerClose">Close</a>
				            </div>
				            
				            <!--// content-->
				        </div>
				    </div>
				</div>
			</form>
			
		</div>
		
	</div>
	
</div>

</section>