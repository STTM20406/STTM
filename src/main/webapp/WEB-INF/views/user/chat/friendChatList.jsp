<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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

		$("#createBtn").on("click", function() {

			if ($("#room_nm").val().length == 0){
				alert("채팅방 이름을 입력하세요");
				return false;
			}
			var test = $("#room_nm").val();
			test.replace("<","&lt;",test);
			test.replace(">","&gt;",test);
			
			var test1 = $("#room_nm").val().replace(/</gi,"&lt;");
			var test2 = test1.replace(/>/gi,"&gt;");
			
			$("#room_nm").val(test2);
			
			//친구추가 할 때 사용하는 체크박스
			var sendArray = new Array();
			var chkbox = $(".checkSelect");
			var sendCnt = 0;

			for (i = 0; i < chkbox.length; i++) {
				if (chkbox[i].checked == true) {
					sendArray[sendCnt] = chkbox[i].value;
					sendCnt++;
				}

			}

			$("#array").val(sendArray);

		});
		
		//채팅방 이름 수정
		$("#updateRoomBtn").on("click", function() {
			
			if($("#room_nmup").val().length == 0){
				alert("수정할 채팅방 이름을 입력하세요");
				return false;
			}
			$("#frmUpdateRoom").submit();
		});
		
		
		//채팅방 이름 범위넘으면 출력
		$('#room_nmup').keyup(function(){
	        if ($(this).val().length > $(this).attr('maxlength')) {
	            alert('입력할 수 있는 제목 범위를 넘었습니다');
	            $(this).val($(this).val().substr(0, $(this).attr('maxlength')));
	        }
   		 });
		
		//채팅방 생성 할 때 이름 범위 
		$('#room_nm ').keyup(function(){
	        if ($(this).val().length > $(this).attr('maxlength')) {
	            alert('입력할 수 있는 제목 범위를 넘었습니다');
	            $(this).val($(this).val().substr(0, $(this).attr('maxlength')));
	        }
   		 });


		
		//프로젝트 생성 버튼 클릭시
		$('.btn-example').on("click", function(){
			
	        var $href = $(this).attr('href');
	        layer_popup($href);
	    });
		
		//채팅방 수정 버튼 클릭 시 
		$('.btn-example1').on("click", function(){
			var a = $(this).attr('id');
			var aarray = a.split('_');
			
			var id = aarray[0];
			var name = aarray[1];
			console.log(aarray[0] + " " + aarray[1]);
			
			$("#upct_id").val(id);
			$("#room_nmup").val(name);
			
			
			
			$("#outRoomBtn").attr('href', '/outChatRoom?ct_id=' + id); 
			console.log($("#outRoomBtn").attr('href'));
	        var $href = $(this).attr('href');
	        layer_popup($href);
	    });
		
		
		$("#faceBtn").on("click",function(){
			window.open('http://localhost/RTCMulticonnection/index.html', '_blank')

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
        	
        	//닫으면 text값 초기화
        	$("#room_nm").val('');
        	
        	
            isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
            return false;
        });

        $('.layer .dimBg').click(function(){
            $('.dim-layer').fadeOut();
            return false;
        });

    }
	
</script>



	<!-- 팝업 새 채팅방 생성 -->
	<div class="dim-layer">
		<div class="dimBg"></div>
		<div id="chatlayer1" class="pop-layer">
			<div class="pop-container">
				<div class="pop-newChat">
					<!--content //-->
					<form action="/createChatRoom" method="post" id="frmCreateRoom">
						<input type="hidden" id="array" name="array">
						<div class="new_proejct">
							<!-- 방 만들기 테이블 -->
							<h2>새 채팅방 생성</h2>
							<ul>
								<li><label for="prj_nm">채팅방 이름</label> <input type="text"
									id="room_nm" name="room_nm" maxlength="20"></li>
								<li><label for="prj_mem">채팅방 친구 선택</label>
									<div class="chat_mem_list">
										<ul>
											<c:forEach items="${allFriendList}" var="friendlist"
												varStatus="status">
												<li><input type="checkbox" name="friend"
													class="checkSelect" value="${friendlist.frd_email}">${friendlist.user_nm }
												</li>
											</c:forEach>
										</ul>

									</div></li>
							</ul>
							<div class="prj_btn">
								 <input
									type="submit" id="createBtn" value="채팅방 만들기">
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


	<!-- 팝업 채팅방 수정/나가기 -->
	<div id="chatlayer2" class="pop-layer">
		<div class="pop-container">
			<!--content //-->
			<form action="/updateChatRoomTitle" method="post" id="frmUpdateRoom">
				<div class="pop-chat">
					<div class="new_proejct">
						<input type="hidden" name="upct_id" id="upct_id">
						<!-- 방 만들기 테이블 -->
						<ul>
							<li><label for="prj_nm">채팅방 이름 수정</label> 
							<input type="text" id="room_nmup" name="room_nmup" maxlength="20"></li>
						</ul>
						<div class="prj_btn">
							<input type="submit" id="updateRoomBtn"  value="채팅방 이름 수정">
							<a href="#"  id="outRoomBtn">채팅방나가기</a>
						</div>
					</div>
				</div>
			</form>
			<div class="btn-r">
				<a href="#" class="btn-layerClose">Close</a>
			</div>
			<!--// content-->
		</div>
	</div>

<section class="contents">

	<form id="frm" action="/friendChat" method="get">
		<input type="hidden" id="ct_id" name="ct_id">
		<input type="hidden" id="what" name="what" value="friend">
	</form>


	<div class="sub_menu">
		<ul class="sub_menu_item">
			<li><a href="/friendChatList">친구 채팅</a></li>
			<li><a id="memChatTab" href="/projectChatList">프로젝트 멤버 채팅</a></li>
			<li><a href="#" id = "faceBtn">화상 회의</a></li>
		</ul>
	</div>
	<div class="sub_btn">
		<ul>
			<li><a href="#chatlayer1" class="btn-example a_style_01">새 채팅방 생성</a></li>
		</ul>
	</div>
	
	<!-- table style start -->
	<table class="tb_style_01">
		<caption>테이블 이름</caption>
		<tr>
			<th>NO</th>
			<th>채팅방 이름</th>
			<th>채팅방 멤버</th>
			<th>채팅방 수정</th>
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
				<td><a href="#chatlayer2" class="btn-example1 btn_style_01" id="${room.ct_id}_${room.ct_nm}">채팅방 수정</a></td>
			</tr>
		</c:forEach>

	</table>


	<!-- table style end -->
	<div style="width: 300px; height: 500px; display: none;"
		id="outChatRoomCheck">
		<input type="button" value="탈퇴" id="outChatRoomOk"> <input
			type="button" value="취소" id="outChatRoomCancel">
	</div>


	<div class="pagination">
                     <c:choose>
                        <c:when test="${pageVo.page == 1 }">
                           <a href class="btn_first"></a>
                        </c:when>
                        <c:otherwise>
                           <a href="${cp}/friendChatList?page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
                        
                        </c:otherwise>
                     </c:choose>
   
                     <c:forEach begin="1" end="${paginationSize}" var="i">
                        <c:choose>
                           <c:when test="${pageVo.page == i}">
                              <span>${i}</span>
                           </c:when>
                           <c:otherwise>
                           <a href="${cp}/friendChatList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
                           </c:otherwise>
                        </c:choose>
   
                     </c:forEach>
   
                     <c:choose>
                        <c:when test="${pageVo.page == paginationSize}">
                           <a href class="btn_last"></a>
                        </c:when>
                        <c:otherwise>
                        <a href="${cp}/friendChatList?page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
                           
   
                        </c:otherwise>
                     </c:choose>
               </div>

</section>





</html>


