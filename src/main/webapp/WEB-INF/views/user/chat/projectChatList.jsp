<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style>
.roomNm:hover{
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
	$(document).ready(function() {
		

		$(".tb_style_01").on("click", "td.roomNm", function() {
			//a = .text();
			var a = $(this).attr("id");
			//$(this).data("ct_id");

			console.log(a);
			$("#ct_id").val(a);

			$("#frm").submit();
		});


		$('ul.tabs li').click(function() {
	         var tab_id = $(this).attr('data-tab');

	         $('ul.tabs li').removeClass('current');
	         $('.tab-content').removeClass('current');

	         $(this).addClass('current');
	         $("#" + tab_id).addClass('current');
	     });
		
		$("#faceBtn").on("click",function(){
			window.open('http://localhost/RTCMulticonnection/index.html', '_blank')

		});
	});

	
</script>



<section class="contents">

	<form id="frm" action="/friendChat" method="get">
		<input type="hidden" id="ct_id" name="ct_id">
		<input type="hidden" id="what" name="what" value="project">
	</form>

	<div class="sub_menu">
		<ul class="sub_menu_item">
			<li><a href="/friendChatList">친구 채팅</a></li>
			<li><a href="/projectChatList">프로젝트 멤버 채팅</a></li>
			<li><a href="#" id = "faceBtn">화상 회의</a></li>
		</ul>
	</div>


	<!-- table style start -->
	<table class="tb_style_01">
		<caption>테이블 이름</caption>
		<tr>
			<th>NO</th>
			<th>프로젝트 이름</th>
			<th>채팅방 멤버</th>
		</tr>

		<!-- 향상된 for -->
		<c:forEach items="${roomlist}" var="room" varStatus="status">
			<tr>
				<td>${room.rn}</td>
				<td id="${room.ct_id }" class="roomNm">${ room.ct_nm }</td>
				<td><c:forEach items="${realRoomMap}" var="friend"
						varStatus="status">
						<c:if test="${friend.key == room.ct_id }">
							<label>${friend.value}</label>
						</c:if>
					</c:forEach></td>
			</tr>
		</c:forEach>

	</table>


	<div class="pagination">
                     <c:choose>
                        <c:when test="${pageVo.page == 1 }">
                           <a href class="btn_first"></a>
                        </c:when>
                        <c:otherwise>
                           <a href="${cp}/projectChatList?page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
                        
                        </c:otherwise>
                     </c:choose>
   
                     <c:forEach begin="1" end="${paginationSize}" var="i">
                        <c:choose>
                           <c:when test="${pageVo.page == i}">
                              <span>${i}</span>
                           </c:when>
                           <c:otherwise>
                           <a href="${cp}/projectChatList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
                           </c:otherwise>
                        </c:choose>
   
                     </c:forEach>
   
                     <c:choose>
                        <c:when test="${pageVo.page == paginationSize}">
                           <a href class="btn_last"></a>
                        </c:when>
                        <c:otherwise>
                        <a href="${cp}/projectChatList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
                           
   
                        </c:otherwise>
                     </c:choose>
               </div>

</section>





</html>


