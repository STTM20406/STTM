<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style type="text/css">
.layer {
	display: none;
	position: fixed;
	_position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 100;
}

.layer .bg {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: #000;
	opacity: .5;
	filter: alpha(opacity = 50);
}

.layer .pop-layer {
	display: block;
}

.pop-layer {
	display: none;
	position: absolute;
	top: 50%;
	left: 50%;
	width: 410px;
	height: auto;
	background-color: #fff;
	border: 5px solid #3571B5;
	z-index: 10;
}

.pop-layer .pop-container {
	padding: 20px 25px;
}

.pop-layer p.ctxt {
	color: #666;
	line-height: 25px;
}

.pop-layer .btn-r {
	width: 100%;
	margin: 10px 0 20px;
	padding-top: 10px;
	border-top: 1px solid #DDD;
	text-align: right;
}

a.cbtn {
	display: inline-block;
	height: 25px;
	padding: 0 14px 0;
	border: 1px solid #304a8a;
	background-color: #3f5a9d;
	font-size: 13px;
	color: #fff;
	line-height: 25px;
}

a.cbtn:hover {
	border: 1px solid #091940;
	background-color: #1f326a;
	color: #fff;
}
</style>

<script>
	$(document).ready(function() {
					

		$("#roomNm").on("click", function() {
			a = $("#roomId").text();
			$(this).data("ct_id");

			console.log(a);
			$("#ct_id").val(a);

			$("#frm").submit();
		});


		$("#showAddFriend").fadeOut(0);
		$("#addFriend").on("click", function() {
			$("#showAddFriend").fadeIn(300);
		});

		//친구추가 할 때 사용하는 체크박스
		var sendArray = new Array();
		var chkbox = $(".checkSelect");
		var sendCnt=0;
		
		for(i=0;i<chkbox.length;i++){
			if(chkbox[i].checked == true){
				sendArray[sendCnt] = chkbox[i].value;
				sendCnt++;
			}
			
		}
		
		
		
		
	});


</script>

<section class="contents">

	<div class="sub_menu">
		<ul class="sub_menu_item">
			<li><a href="/friendChatList">친구 채팅</a></li>
			<li><a href="/projectChat">프로젝트 멤버 채팅</a></li>
			<li><a href="/faceChatMain">화상 회의</a></li>
		</ul>
		<div class="sub_btn">
			<ul>
				<li><input type="button" id="createRoom" value="+ 새 채팅방 생성"></li>
			</ul>
		</div>
	</div>

	<!-- 방 만들기 버튼 클릭 시, 보여짐 -->
	<div style="width: 300px; height: 500px; display: none;"
		class="ui message" id="showCreateRoom">
		<!-- 방 만들기 테이블 -->
		<table style="width: 100/5; height: 100%;">
			<col width="80px">
			<!-- 방제목 -->
			<tr style="padding: 1px; margin: 1px;">
				<th>방제목</th>
				<td><input type="text" name="name" placeholder="방이름" size="8"
					class="ui message"
					style="font-weight: bold; width: 100%; height: 15px"></td>
			</tr>


			<!-- 버튼 처리 -->
			<tr>
				<td colspan="2"><input type="button" id="submitBtn"
					value="방만들기" class="ui primary button"> <input
					type="button" id="backBtn" value="돌아가기" class="ui button">
				</td>
			</tr>
		</table>

	</div>

	<form id="frm" action="/friendChat" method="get">
		<input type="hidden" id="ct_id" name="ct_id">
	</form>


	<!-- table style start -->
	<table class="tb_style_01">
		<caption>테이블 이름</caption>
		<tr>
			<th>NO</th>
			<th>채팅방 이름</th>
			<th>채팅방 멤버</th>
			<th>친구 추가</th>
		</tr>

		<!-- 향상된 for -->
		<c:forEach items="${roomlist}" var="room" varStatus="status">
			<tr>
				<td style="display: none" id="roomId">${room.ct_id }</td>
				<td>${room.ct_id }</td>
				<td id="roomNm">${ room.ct_nm }</td>
				<td>
					<select>
							<c:forEach items="${roomFriendList}" var="friend" varStatus="status">
								<option>${friend }</option>
							</c:forEach>
					</select>
				</td>
				<td><input type="button" value="친구 추가" id="addFriend"></td>
				<td><a
					href="/outChatRoom?ct_id=${room.ct_id}&user_email=${USER_INFO.user_email}"
					style="color: red;">채팅방나가기</a></td>
			</tr>
		</c:forEach>



	</table>

	<div class="modal fade" style="width: 300px; height: 500px;" id="showAddFriend">

		<c:forEach items="${friendsList}" var="friend" varStatus="status">

			<input type="checkbox" name="friendList" value="${ friend.user_nm}">${friend.user_nm }
   		 	
   		 </c:forEach>
		<input type="button" value="친구 추가" id="addFriendBtn">
   		 

	</div>


	<!-- table style end -->
	<div style="width: 300px; height: 500px; display: none;"
		id="outChatRoomCheck">
		<input type="button" value="탈퇴" id="outChatRoomOk"> <input
			type="button" value="취소" id="outChatRoomCancel">
	</div>


	<div class="pagination">
		<a href="" class="btn_first"></a> <span>1</span> <a href="">2</a> <a
			href="">3</a> <a href="" class="btn_last"></a>
	</div>

</section>





</html>