<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<section class="contents">
	<input type="hidden" id="ct_id" name="ct_id" value="${ct_id }">
	<input type="hidden" id="user_nm" value="${USER_INFO.user_nm }">
	<input type="hidden" id="user_email" value="${USER_INFO.user_email}">
	
	
	<div class="sub_menu">
		<ul class="sub_menu_item">
			<li><a href="/friendChatList">친구 채팅</a></li>
			<li><a href="/projectChat">프로젝트 멤버 채팅</a></li>
			<li><a href="/faceChatMain">화상 회의</a></li>
		</ul>
	</div>

	<!-- popup 친구 추가-->
	<div class="dim-layer">
		<div class="dimBg"></div>
		<div id="layer1" class="pop-layer">
			<div class="pop-container">
				<div class="pop-project">
					<!--content //-->
					<form action="/addFriend" method="post" id="addFriend">
						<input type="hidden" id="array" name="array" >
						<input type="hidden" id="ct_id" name="ct_id" value="${ct_id }">
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
								<a href="javascript:;" id="prj_btn_prev">뒤로</a> <input
									type="submit" id="prj_btn_submit" value="친구 추가">
							</div>
						</div>

					</form>

					<div class="btn-r">
						<a href="#" class="btn-layerClose">Close</a>
					</div>
					<!--// content-->
				</div>
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
				<br> <br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<a href="#layer1" class="btn-example btn_style_01">친구 추가</a>
				<br>
				<br> <br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br> <input type="button" value="채팅방 리스트로 가기">
			</div>
		</div>
		<div class="chat_room">
			<div class="chat_room_hd">
				<h2>${roomNm }</h2>
			</div>
			<div class="chat_room_con">
				<c:forEach items="${chatroomContentList }" var="contentList">
					<c:if test="${contentList.user_email != USER_INFO.user_email }">
						<br>
						<dl class="chat_other">
							<dt id="otherName">${contentList.user_nm }</dt>
							<dd id="otherContent">${contentList.ch_msg }</dd>
						</dl>

					</c:if>
					<c:if test="${contentList.user_email == USER_INFO.user_email }">
						<br>
						<dl class="chat_me">
							<dt id="meName">${contentList.user_nm }</dt>
							<dd id="meContent">${contentList.ch_msg }</dd>
						</dl>
					</c:if>

				</c:forEach>
			</div>
			<div class="chat_room_bt">
				<input type="text" id="msg" name="msg"
					placeholder="write something.." value=""> <input
					type="button" id="buttonMessage" value="보내기">
			</div>
		</div>


	</div>

</section>


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.0/sockjs.min.js"></script>
<script>
	$(document).ready(
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
								let socketMsg = "chatting," + senderNm + ","
										+ content + "," + senderId1 + ","
										+ ct_id; //소켓으로 이 정보를 보냄
								console.log("sssssssmsg>>", socketMsg);
								socket.send(socketMsg);
							}

						});

				$("#addFriend").on('click', function(){
					
					var array = Array();
					var cnt = 0;
					var chkbox = $(".friend");
					
					for(i=0;i<chkbox.length;i++){
						if(chkbox[i].checked == true){
							array[cnt] = chkbox[i].value;
							cnt++;
						}
					}
				
					$("#array").val(array);
					console.log($("#array").val());
				});
			
				$('.btn-example').on("click", function(){
					
			        var $href = $(this).attr('href');
			        layer_popup($href);
			    });
			
			});
	
	
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

<script>
	var socket = null;
	function connect() {
		//var ws = new WebSocket("ws://localhost:8090/echo.do");
		var ct_id = $('#ct_id').val();
		var userId = $("#user_email").val();
		var userNm = $("#user_nm").val();
		socket = new SockJS("/echo.do");

		socket.onopen = function() {
			console.log('Info : connection opened');
			//setTimeout(function(){connect(); }, 1000); //retry connection;

			$("#participate").append("<li>" + userNm + "</li>");

		};

		socket.onmessage = function(event) {
			console.log("ReceiveMessage: ", event.data + "\n");
			var strArray = event.data.split(",");

			console.log("strArray[0] :" + strArray[0] + "strArray[1]"
					+ strArray[1] + "strArray[2]" + strArray[2]);

			if (strArray[0] == userId) {
				$(".chat_me").append(
						"<br><br>" + strArray[1] + "<br>" + strArray[2]);
			} else {
				$(".chat_other").append(
						"<br><br>" + strArray[1] + "<br>" + strArray[2]);
			}

		};

		socket.onclose = function(event) {
			console.log('info: connection closed');
		};

		socket.onerror = function(err) {
			console.log('error: ', err);
		};

	}
</script>








