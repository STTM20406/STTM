<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style type="text/css">
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

		$("#createBtn").on("click", function() {

			if ($("#room_nm").val().length == 0)
				alert("채팅방 이름을 입력하세요");

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
			$("#frmUpdateRoom").submit();
		});

		//프로젝트 생성 버튼 클릭시
		$('.btn-example').on("click", function(){
			
	        var $href = $(this).attr('href');
	        layer_popup($href);
	    });
		
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
		<div id="layer1" class="pop-layer">
			<div class="pop-container">
				<div class="pop-project">
					<!--content //-->
					<form action="/createChatRoom" method="post" id="frmCreateRoom">
						<input type="hidden" id="array" name="array">
						<div class="new_proejct">
							<!-- 방 만들기 테이블 -->
							<ul>
								<li><label for="prj_nm">새 채팅방 생성</label></li>
								<li><label for="prj_nm">채팅방 이름</label> <input type="text"
									id="room_nm" name="room_nm"></li>
								<li><label for="prj_mem">채팅방 친구 선택</label>
									<div class="prj_mem_list">
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
								<a href="javascript:;" id="prj_btn_prev">뒤로</a> <input
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
<!-- 		<div class="dim-layer"> -->
<!-- 		<div class="dimBg"></div> -->
		<div id="layer2" class="pop-layer">
			<div class="pop-container">
				<div class="pop-project">
					<!--content //-->
					<form action="/updateChatRoomTitle" method="post" id="frmUpdateRoom">
						<div class="new_proejct">
							<input type="hidden" name="upct_id" id="upct_id">
							<!-- 방 만들기 테이블 -->
							<ul>
								<li><label for="prj_nm">채팅방 이름 수정</label> 
								<input type="text" id="room_nmup" name="room_nmup"></li>
							</ul>
							<div class="prj_btn">
								<input type="submit" id="updateRoomBtn" value="채팅방 이름 수정">
							</div>
							<a href="#"  id="outRoomBtn" style="color: red;">채팅방나가기</a>
							
						</div>

					</form>
					
					
					<div class="btn-r">
						<a href="#" class="btn-layerClose">Close</a>
					</div>
					<!--// content-->
				</div>
			</div>
		</div>
<!-- 	</div> -->

<section class="contents">

	<form id="frm" action="/friendChat" method="get">
		<input type="hidden" id="ct_id" name="ct_id">
		<input type="hidden" id="what" name="what" value="friend">
	</form>

	<div class="sub_menu">
		<ul class="sub_menu_item">
			<li><a href="/friendChatList">친구 채팅</a></li>
			<li><a href="/projectChatList">프로젝트 멤버 채팅</a></li>
			<li><a href="#" id = "faceBtn">화상 회의</a></li>
		</ul>
		<div class="sub_btn">
			<ul>
				<li><a href="#layer1" class="btn-example btn_style_01">새 채팅방 생성</a></li>
			</ul>
		</div>
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
				<td><a href="#layer2" class="btn-example1 btn_style_01" id="${room.ct_id}_${room.ct_nm}">채팅방 수정</a></td>
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
		<a href="" class="btn_first"></a> <span>1</span> <a href=""
			class="btn_last"></a>
	</div>

</section>





</html>


