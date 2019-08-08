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
	
	$("#tab-2").on("click",function(){
		$("#frdEmailFrom").submit();
	});
	
});	

//------- 모달 설정 스크립트 -------
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

// function deleteFriends() {
// 	$("#btnDeleteFriends").on("click",function(){
// //			$("#profileForm").submit();
// 		alert("친구가 삭제 되었습니다.");			
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
			
			<input type="button" class="btn_style_01" onclick="layer_open('layer3');return false;" value="친구등록">
		
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
		
			<div class="layer">
				<div class="bg"></div>
				<div id="layer3" class="pop-layer">
					<div class="pop-container">
						<div class="pop-conts">
							<!--content //-->
							<p class="ctxt mb20">
							친구 추가 입니다
							</p>
								<input type="text" id="" name="" value="">
								<input type="text" id="" name="" value="">

							
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
		
	</div>
	
</div>

</section>