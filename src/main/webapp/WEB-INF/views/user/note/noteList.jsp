<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.rcvTr:hover{
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
		
		$("#search").on("change",function(){
			console.log($("#search").val());
			var searchValue = $("#search").val();
			$("#scText").val(searchValue);
		})
		
		$("#sch_submit").on("click",function(){
			$("#frmSearch").submit();
		})
		
		$('ul.tabs li').click(function() {
			var tab_id = $(this).attr('data-tab');

			$('ul.tabs li').removeClass('current');
			$('.tab-content').removeClass('current');

			$(this).addClass('current');
			$("#" + tab_id).addClass('current');
		});
		
		$(".rcvTr").on("click",function(){
			console.log("rcvTr click");
			var aTag = $('#aTag').attr('href');
			console.log(aTag);
			layer_popup(aTag);
			
			
			
		})
	});
</script>

<!-- 쪽지 상세내용 팝업 -->
		<div id="layer1" class="pop-layer">
			<div class="pop-container">
				<div class="pop-project">
					<!--content //-->
						<input type="hidden" id="array" name="array">
						<div class="new_proejct">
							<!-- 방 만들기 테이블 -->
							<ul>
								<li><label for="prj_nm">새 채팅방 생성</label></li>
								<li><label for="prj_nm">채팅방 이름</label> <input type="text"
									id="room_nm" name="room_nm"></li>
								<li>
									<label for="prj_mem">채팅방 친구 선택</label>
									<div class="prj_mem_list">
									
									</div>
								</li>
							</ul>
							<div class="prj_btn">
								<a href="javascript:;" id="prj_btn_prev">뒤로</a> <input
									type="submit" id="createBtn" value="채팅방 만들기">
							</div>
						</div>


					<div class="btn-r">
						<a href="#" class="btn-layerClose">Close</a>
					</div>
					<!--// content-->
				</div>
			</div>
		</div>

<section class="contents">

	<form id="frm" action="/admInquiryView" method="get"> 
		<input type="hidden" id="inq_id" name="inq_id" value=""/>
		
	</form>
	
	<div id="container">


		<div class="sub_menu">
			<ul class="tabs">
				<li data-tab="tab-1">받은 쪽지함</li>
				<li data-tab="tab-2">보낸 쪽지함</li>
			</ul>
		</div>

		<div class="tab_con">
<!-- 1번 탭 -->
			<div id="tab-1" class="tab-content current">
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
								<th>보낸 사람</th>
								<th>내용</th>
								<th>받은 날짜</th>
								<th>쪽지 읽음 여부</th>
								<th>수신자 쪽지 삭제 여부</th>

								<c:forEach items="${rcvList }" var="rcv">
									<c:choose>
										<c:when test="${rcv.rcv_del_fl == 'N'}">
											<tr class="rcvTr" >
												<td class="inquirynum">${rcv.send_email }</td>
												<td>${rcv.note_con }</td>
												<td><fmt:formatDate value="${rcv.rcv_date }" pattern="yyyy-MM-dd"/></td>
												<td>${rcv.read_fl }</td>
												<td>${rcv.rcv_del_fl }</td>
												<td><a id="aTag" href="#layer1" class="btn-example1">12</a></td>
											</tr>
										</c:when>
									</c:choose>
								</c:forEach>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="pagination">
						<c:choose>
							<c:when test="${pageVo.page == 1 }">
								<a href class="btn_first"></a>
							</c:when>
							<c:otherwise>
								<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
							
							</c:otherwise>
						</c:choose>

						<c:forEach begin="1" end="${rcvPaginationSize}" var="i">
							<c:choose>
								<c:when test="${pageVo.page == i}">
									<span>${i}</span>
								</c:when>
								<c:otherwise>
								<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
								</c:otherwise>
							</c:choose>

						</c:forEach>

						<c:choose>
							<c:when test="${pageVo.page == rcvPaginationSize}">
								<a href class="btn_last"></a>
							</c:when>
							<c:otherwise>
							<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
								

							</c:otherwise>
						</c:choose>
				
				</div>
			</div>
<!-- 2번탭 -->
			<div id="tab-2" class="tab-content">
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
								<th>받는 사람</th>
								<th>내용</th>
								<th>보낸 날짜</th>
								<th>쪽지 읽음 여부</th>
								<th>발신자 쪽지 삭제 여부</th>

								<c:forEach items="${sendList }" var="send">
									<c:choose>
										<c:when test="${send.send_del_fl == 'N'}">
											<tr class="sendTr">
												<td class="inquirynum">${send.rcv_email }</td>
												<td>${send.note_con }</td>
												<td><fmt:formatDate value="${send.send_date }" pattern="yyyy-MM-dd"/></td>
												<td>${send.read_fl }</td>
												<td>${send.send_del_fl }</td>
											</tr>
										</c:when>
									</c:choose>
								</c:forEach>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="pagination">
						<c:choose>
							<c:when test="${pageVo.page == 1 }">
								<a href class="btn_first"></a>
							</c:when>
							<c:otherwise>
								<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${pageVo.page - 1}&pageSize=${pageVo.pageSize}">«</a>
							
							</c:otherwise>
						</c:choose>

						<c:forEach begin="1" end="${sendPaginationSize}" var="i">
							<c:choose>
								<c:when test="${pageVo.page == i}">
									<span>${i}</span>
								</c:when>
								<c:otherwise>
								<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
								</c:otherwise>
							</c:choose>

						</c:forEach>

						<c:choose>
							<c:when test="${pageVo.page == sendPaginationSize}">
								<a href class="btn_last"></a>
							</c:when>
							<c:otherwise>
							<a href="${cp}/admInquiry?inq_cate=${pageVo.inq_cate }&page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>

							</c:otherwise>
						</c:choose>
				</div>
		</div>
	</div>

	</div>
</section>