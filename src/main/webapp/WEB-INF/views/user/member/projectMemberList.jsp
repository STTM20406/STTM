<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



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
	   console.log(socket.readyState);
	   if(socket.readyState == 1)
		   socket.send("prjMem,${USER_INFO.user_email}");
	   
	   socket.onclose = function(event) {
		      console.log('info: MemberConnection closed');
		   };

	   socket.onerror = function(err) {
	      console.log('error: ', err);
	   };
	   
});	


</script>

<!-- <div class="sub_menu"> -->
<!-- 	<ul class="tabs"> -->
<!-- 		<li data-tab="tab-1"> -->
<!-- 		<a href="/projectMember">프로젝트 멤버</a> -->
<!-- 		</li> -->
<!-- 		<li data-tab="tab-2"> -->
<!-- 			<a href="/friendsList">친구 리스트</a> -->
<!-- 		</li> -->
<!-- 	</ul> -->
<!-- </div> -->

<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="/projectMember">Project Member</a></li>
		<li><a href="/friendsList">Friend List</a></li>
	</ul>
</div>

<section class="contents">
	<div class="project_wrap">
		<h2>프로젝트 목록</h2>
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
										<td><a href="/friendsList?frdRequEmail=${prjVo.user_email}" id="friendReqAtag" class="inp_style_01">친구요청</a></td>
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
								<a href="javascript:;" class="btn_last"></a>
							</c:when>
							<c:otherwise>
								<a href="${cp}/projectMemberList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
							</c:otherwise>
						</c:choose>
				
				</div>
			</div>
		</div>
	</div>
</section>