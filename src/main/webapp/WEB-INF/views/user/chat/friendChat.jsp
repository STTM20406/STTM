<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="/friendChatList">친구 채팅</a></li>
		<li><a href="/projectChatList">프로젝트 멤버 채팅</a></li>
		<li><a href="#" id="faceBtn">화상 회의</a></li>
	</ul>
</div>

<section class="contents">
	<input type="hidden" id="ct_id" name="ct_id" value="${ct_id }">
	<input type="hidden" id="user_nm" value="${USER_INFO.user_nm }">
	<input type="hidden" id="user_email" value="${USER_INFO.user_email}">

	<!-- popup 친구 추가-->
	<div class="dim-layer">
		<div class="dimBg"></div>
		<div id="layer1" class="pop-layer">
			<div class="pop-container">
				<!--content //-->
				<div class="pop-addFriend">
					<form action="/addFriend" method="post" id="addFriend">
						<input type="hidden" id="array" name="array"> <input
							type="hidden" id="ct_id" name="ct_id" value="${ct_id }">
						<div class="new_proejct">
							<!-- 방 만들기 테이블 -->
							<ul>
								<li><label for="prj_nm">친구 추가</label></li>
								<li><label for="prj_nm">채팅방 이름</label></li>
								<li><label for="prj_mem">추가할 친구 선택</label>
									<div class="prj_mem_list">
										<ul>
											<c:forEach items="${inviteList}" var="friendlist"
												varStatus="status">
												<li><input type="checkbox" name="friend" class="friend"
													value="${friendlist.user_email }">${friendlist.user_nm }
												</li>
											</c:forEach>
										</ul>

									</div></li>
							</ul>
							<div class="prj_btn">
								<input type="submit" id="prj_btn_submit" value="친구 추가">
							</div>
						</div>
					</form>
				</div>

				<div class="btn-r">
					<a href="#" class="btn-layerClose">Close</a>
				</div>
				<!--// content-->
			</div>
		</div>
	</div>


	<div class="chat_wrap">
		<div class="chat_list">
			<div class="chat_friends">
				<h2>채팅방 멤버</h2>
				<ul>
					<c:forEach items="${friendList }" var="friendlist">
						<li>${friendlist }</li>
					</c:forEach>
				</ul>
				<div class="chatBtn">
					<c:if test="${what != 'project'}">
						<a href="#layer1" class="btn-example">친구 추가</a>
					</c:if>
					<c:if test="${what != 'project'}">
						<a href="/friendChatList">채팅방 리스트 이동</a>
					</c:if>
					<c:if test="${what == 'project'}">
						<a href="/projectChatList">채팅방 리스트 이동</a>
					</c:if>
				</div>
			</div>
		</div>
		<div class="chat_room">
			<div class="chat_room_hd">
				<div class="mesgs">
					<div class="msg_history" id="chatData">
						<c:forEach items="${chatroomContentList }" var="contentList">
							<c:if test="${contentList.user_email != USER_INFO.user_email }">
								<div class="incoming_msg">
									<div class="received_msg">
										<div class="received_withd_msg">
											<p>${contentList.user_nm }</p>
											<p>${contentList.ch_msg }</p>
											<span class="time_date"><fmt:formatDate
													value="${contentList.ch_msg_dt }" pattern="yy/MM/dd HH:mm" /></span>
										</div>
									</div>
								</div>

							</c:if>
							<c:if test="${contentList.user_email == USER_INFO.user_email }">
								<div class="outgoing_msg">
									<div class="sent_msg">
										<p>${contentList.user_nm }</p>
										<p>${contentList.ch_msg }</p>
										<span class="time_date"><fmt:formatDate
												value="${contentList.ch_msg_dt }" pattern="yy/MM/dd HH:mm" /></span>
									</div>
								</div>

							</c:if>
						</c:forEach>


					</div>
					<div class="type_msg">
						<div class="type_msg">
							<div class="input_msg_write">
								<input type="text" id="msg" name="msg" class="write_msg"
									placeholder="Type a message" />
								<button class="msg_send_btn" id="buttonMessage" type="button">
									<i class="fa fa-paper-plane-o" aria-hidden="true"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 	<div class="messaging"> -->
	<!-- 		<div class="inbox_msg"> -->
</section>


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.0/sockjs.min.js"></script>
<script>
	$(document)
			.ready(
					function() {
						connect();
						$("#buttonMessage").on(
								"click",
								function(evt) {
									evt.preventDefault();

									if (socket.readyState !== 1)
										return;

									console.debug("socket : ", socket);

									// 			소켓으로 보낼 정보들
									let senderNm = $('#user_nm').val();
									let content = $('#msg').val();
									let senderId1 = $("#user_email").val();
									let ct_id = $("#ct_id").val();

									if (socket) {
										let socketMsg = "chatting," + senderNm
												+ "," + content + ","
												+ senderId1 + "," + ct_id; //소켓으로 이 정보를 보냄
										console.log("sssssssmsg>>", socketMsg);
										socket.send(socketMsg);
									}

									$("#msg").val('');
									$("#msg").focus();
								});

						$("#addFriend").on('click', function() {

							var array = Array();
							var cnt = 0;
							var chkbox = $(".friend");

							for (i = 0; i < chkbox.length; i++) {
								if (chkbox[i].checked == true) {
									array[cnt] = chkbox[i].value;
									cnt++;
								}
							}

							$("#array").val(array);
							console.log($("#array").val());
						});

						$('.btn-example').on("click", function() {

							var $href = $(this).attr('href');
							layer_popup($href);
						});

						$("#faceBtn")
								.on(
										"click",
										function() {
											window
													.open(
															'http://localhost/RTCMulticonnection/index.html',
															'_blank')

										});
					});

	window.onkeyup = function(e) {
		var code = e.keyCode || e.which;
		if (code == 13) {
			$('#buttonMessage').click();
		}
	};

	//layer popup - 프로젝트 생성
	function layer_popup(el) {
		console.log(el);

		var $el = $(el); //레이어의 id를 $el 변수에 저장
		var isDim = $el.prev().hasClass('dimBg'); //dimmed 레이어를 감지하기 위한 boolean 변수

		isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

		var $elWidth = ~~($el.outerWidth()), $elHeight = ~~($el.outerHeight()), docWidth = $(
				document).width(), docHeight = $(document).height();

		// 화면의 중앙에 레이어를 띄운다.
		if ($elHeight < docHeight || $elWidth < docWidth) {
			$el.css({
				marginTop : -$elHeight / 2,
				marginLeft : -$elWidth / 2
			})
		} else {
			$el.css({
				top : 0,
				left : 0
			});
		}

		$el.find('a.btn-layerClose').click(function() {
			isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
			return false;
		});

		$('.layer .dimBg').click(function() {
			$('.dim-layer').fadeOut();
			return false;
		});

	}
</script>

<script>
	// 	var socket = null;
	function connect() {
		//var ws = new WebSocket("ws://localhost:8090/echo.do");
		var ct_id = $('#ct_id').val();
		var userId = $("#user_email").val();
		var userNm = $("#user_nm").val();
		//socket = new SockJS("/echo.do");

		// 		socket.onopen = function() {
		// 			console.log('Info : connection opened');
		// 			//setTimeout(function(){connect(); }, 1000); //retry connection;

		// 			$("#participate").append("<li>" + userNm + "</li>");

		// 		};

		socket.onmessage = function(event) {
			console.log("ReceiveMessage: ", event.data + "\n");
			var strArray = event.data.split(",");

			console.log("strArray[0] :" + strArray[0] + "strArray[1]"
					+ strArray[1] + "strArray[2]" + strArray[2] + "userId"
					+ userId);

			if (strArray[0] != userId) {
				var printHTML = "<div class='incoming_msg'>";
				printHTML += "<div class='received_msg'>";
				printHTML += "<div class='received_withd_msg'>";
				printHTML += "<p>" + strArray[1] + "</p>";
				printHTML += "<p>" + strArray[2] + "</p>";
				printHTML += "<span class='time_date'>" + strArray[3]
						+ "</span></div></div></div>";
				$("#chatData").append(printHTML);
				$("#chatData").scrollTop($("#chatData")[0].scrollHeight);

			} else {
				var printHTML = "<div class='outgoing_msg'>";
				printHTML += "<div class='sent_msg'>";
				printHTML += "<p>" + strArray[1] + "</p>";
				printHTML += "<p>" + strArray[2] + "</p>";
				printHTML += "<span class='time_date'>" + strArray[3]
						+ "</span></div></div></div>";
				$("#chatData").append(printHTML);
				$("#chatData").scrollTop($("#chatData")[0].scrollHeight);
			}

		};

		// 		socket.onclose = function(event) {
		// 			console.log('info: connection closed');
		// 		};

		socket.onerror = function(err) {
			console.log('error: ', err);
		};

	}
</script>








