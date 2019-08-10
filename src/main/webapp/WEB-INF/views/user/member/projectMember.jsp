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
	
</style>

<script>
$(document).ready(function(){
	
// 	$(".userTr").on("click", function(){
		
// 		var user_email = $(this).find(".user_email").text();
// 		$("#prjMemList").val(user_email);
		
// 		$("#prjMemForm").submit();
// 	});
	
	// ------- 탭 설정 -------
	$('ul.tabs li').click(function() {
		var tab_id = $(this).attr('data-tab');

		$('ul.tabs li').removeClass('current');
		$('.tab-content').removeClass('current');

		$(this).addClass('current');
		$("#" + tab_id).addClass('current');
	});
	
	// ------- 설정 버튼 -------
// 	$(".user_set_list").hide();
// 	$(".icon_set").on("click", function(){
// 		$(".user_set_list").fadeIn();
// 	});
// 	$(".user_set_list").on("mouseleave", function(){
// 		$(".user_set_list").fadeOut();
// 	});
	
	// 요청 반은 친구 목록 폼 넘기기
// 	$("#tab-2").on("click",function(){
// 		$("#friendsRequestListForm").submit();
// 	});
	
	// 요청 받은 친구 목록 클릭시 넘기기
// 	$("#btnFriendsReqList").on("click", function(){
// 		$("#friendsRequestListForm").submit();
// 	});
	
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
			
			<!--  -->
	<!-- 		<form id="prjMemForm" action="projectMemberList" method="get"> -->
	<!-- 			<input type="text" id="prjMemList" name="prjMemList" > -->
	<!-- 		</form> -->
				
			<div>
				<table class="tb_style_01">
					<colgroup>
						<col width="10%">
						<col width="40%">
						<col width="30%">
						<col width="10%">
						<col width="10%">
					</colgroup>
					<tbody>
						<tr>
						
							<th>사용자 이메일</th>
							<th>사용자 이름</th>
		
							<c:forEach items="${projectMemList}" var="prjVo">
							
								<tr class="userTr" data-user_email="${prjVo.user_email}">
									<td class="user_email" onclick="layer_open('layer2');return false;">${prjVo.user_email}</td>
									<td>${prjVo.user_nm}</td>
								</tr>
								
							</c:forEach>
							
						</tr>
					</tbody>
				</table>
			</div>
			
	<!-- 		<a href="/admInsertUser" class="btn_style_01">사용자 등록</a> -->
		
			<div class="pagination">
					<c:choose>
						<c:when test="${pageVo.page == 1 }">
							<a href class="btn_first"></a>
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
		
			<div class="layer">
				<div class="bg"></div>
				<div id="layer2" class="pop-layer">
					<div class="pop-container">
						<div class="pop-conts">
							<!--content //-->
							<p class="ctxt mb20">
							
							이게 나와야 일을 합니다.
							
							</p>
							
								<input type="button" id="" name="" onclick="" class="btn_style_01" value="친구추가">						
			
							<div class="btn-r">
								<a href="#" class="cbtn">Close</a>
							</div>
							<!--// content-->
						</div>
					</div>
				</div>
			</div>
			
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
				
				<table class="tb_style_01">
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
							
								<tr class="userTr" data-user_email="${prjVo.user_email}">
<%-- 									<td class="user_email">${prjVo.user_email}</td> --%>
									<td>${prjVo.user_nm}</td>
									<td>${prjVo.frd_email}</td>
									<td class="delFriends">
										<a href="/deleteFriends?frd_email=${prjVo.frd_email}" class="frdDel">삭제하기</a>
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
							<a href="${cp}/friendsSearchList?page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
						
						</c:otherwise>
					</c:choose>
		
					<c:forEach begin="1" end="${paginationSize}" var="i">
						<c:choose>
							<c:when test="${pageVo.page == i}">
								<span>${i}</span>
							</c:when>
							<c:otherwise>
							<a href="${cp}/friendsSearchList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
							</c:otherwise>
						</c:choose>
		
					</c:forEach>
		
					<c:choose>
						<c:when test="${pageVo.page == paginationSize}">
							<a href class="btn_last"></a>
						</c:when>
						
						<c:otherwise>
							<a href="${cp}/friendsSearchList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
						</c:otherwise>
					</c:choose>
			
			</div>
		
			<form action="/friendsRequestList" id="friendsRequestListForm" method="get">
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
										
											<tr class="userTr" data-user_email="${prjVo.user_email}">
												<td>${friReqList.user_nm}</td>
												<td>1</td>
												<td>2</td>
											</tr>
											
											<td>1</td>
											<td>2</td>
																						
										</c:forEach>
										
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
			
				            <p class="ctxt mb20">
							친구 등록
							</p>
			
							<input type="text" id="req_email" name="req_email" value="${req_email}">
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