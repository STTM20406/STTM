<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- basic Library --%>
<%@include file="/WEB-INF/views/common/baseLib.jsp"%>

<style>
	
	.userTr:hover{
		cursor: pointer;
	}
	
	.logout {
		color: #e1e1e1;
	}
	
	.logon {
		color: #0ceb47;
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
	    	  if(prjTable) {
	     		  var prjTr = $(prjTable).find("tr");
	    		  ids.forEach(function(id) {
	     			  $(prjTr).each(function(){
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
});	


</script>

<section class="contents">
	
	<div class="sub_menu">
			<ul class="tabs">
				<li data-tab="tab-1">
					<a href="/projectMember">프로젝트 멤버</a>
				</li>
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
								<input type="hidden" value="${prjMemFriList}">
								<tr class="prjMemTr" data-user_email="${prjVo.user_email}">
									<td class="user_email" id="${prjVo.user_email}">
										<a href="#layer2" class="prjMember">${prjVo.user_email}</a><span id="log_${prjVo.user_email }" class="logout"> ●</span>
									</td>
									
									<td>${prjVo.user_nm}</td>
									
									<td>
										
										<c:forEach items="${prjMemFriList}" var="friVo">
												
											<c:choose>

												<c:when test="${prjVo.user_email eq friVo.user_email or prjVo.user_email eq friVo.frd_email}">
													
												</c:when>

<%-- 												<c:when test="${prjVo.user_email eq friVo.frd_email}"> --%>

<%-- 												</c:when> --%>
												
												<c:otherwise>
													<a href="/projectMemberList?frdRequEmail=${prjVo.user_email}" id="friendReqAtag" class="inp_style_01">친구요청</a>
												</c:otherwise>
												
											</c:choose>	
												
										</c:forEach>										
											
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
			
	</div>
	
		<div id="tab-2" class="tab-content">
			
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
			                </form>
		            	</fieldset>
		           	</div>
	          	</div>
				
				<table class="tb_style_01" id="friendTable">
					<colgroup>
						<col width="30%">
						<col width="30%">
						<col width="30%">
					</colgroup>
					<tbody>
						<tr>
						
							<th>친구 이름</th>
							<th>친구 이메일</th>
							<th>친구삭제</th>
		
							<c:forEach items="${friendsList}" var="prjVo">
							
								<tr class="userTr" data-user_email="${prjVo.frd_email}">
									<td>${prjVo.user_nm}</td>
									<td>${prjVo.frd_email}<span class="logout"> ●</span></td>
									<td class="delFriends">
										
										<a href="/deleteFriends?frd_email=${prjVo.frd_email}" class="a_style_04">삭제하기</a>
										
									</td>
								</tr>
								
							</c:forEach>
							
						</tr>
					</tbody>
				</table>
			</div>
			
			<a href="#layer3" class="requestedFriendsList a_style_01">요청 받은 친구 목록</a>
			<a href="#layer4" class="friendsBtn a_style_01">친구 요청</a>
			
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

</section>