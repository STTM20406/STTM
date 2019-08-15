<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.rcvTr:hover{
		cursor: pointer;
}
.sendTr:hover{
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
			var email = $(this).find('#sendEmail').text();
			var con = $(this).find('#rcvCon').text();
			var date = $(this).find('#rcvDate').text();

			$("#lbEmail").text(email);
			$("#smarteditor").val(con);
			$("#lbDate").text(date);
			$("#rcvEmaildInput").val(email);
			layer_popup(aTag);
		})
		
		$(".sendTr").on("click",function(){
			console.log("sendTr click");
			var aTag = $('#aTag02').attr('href');
			var email = $(this).find('#sendEmail').text();
			var con = $(this).find('#sendCon').text();
			var date = $(this).find('#sendDate').text();

			$("#lbEmail02").text(email);
			$("#smarteditor02").val(con);
			$("#lbDate02").text(date);
			layer_popup(aTag);
		})
		
		$("#rcvBtn").on('click',function(){
			console.log("rcvBtn Click")
			$(".rcvFrm").attr("action","/rcvNoteWrite");
			$(".rcvFrm").attr("method","GET");
			$(".rcvFrm").submit();
			
			
		})
	});
</script>

<!-- 받은 쪽지 상세내용 팝업 -->
<form class="rcvFrm">
		<div id="layer1" class="pop-layer">
			<div class="pop-container">
				<div class="pop-project">
					<!--content //-->
						<input type="hidden" id="array" name="array">
						<div class="new_proejct">
							<!-- 방 만들기 테이블 -->
							<ul>
								<li>
									<label>보낸사람 : </label>
									<label id="lbEmail"></label>
									<input type="hidden" name="rcvEmail" id="rcvEmaildInput" value=""/>
								</li>
								<li>
									<label>받은 날짜 : </label>
									<label id="lbDate"></label>
								</li>
								<li>
									<div>
										<br><textarea name="smarteditor" readonly="readonly" id="smarteditor" rows="10" cols="100" style="width: 460px; height: 330px;"></textarea>
									</div>
								</li>
							</ul>
						</div>
					<div class="btn-r">
						<button type="button" id="rcvBtn" class="rcvbtn-style"> 답장</button>
						<a href="#" class="btn-layerClose">Close</a>
					</div>
					<!--// content-->
				</div>
			</div>
		</div>
</form>
		
<!-- 보낸 쪽지 상세내용 팝업 -->
		<div id="layer2" class="pop-layer">
			<div class="pop-container">
				<div class="pop-project">
					<!--content //-->
						<input type="hidden" id="array" name="array">
						<div class="new_proejct">
							<!-- 방 만들기 테이블 -->
							<ul>
								<li>
									<label>보낸사람 : </label>
									<label id="lbEmail02"></label>
								</li>
								<li>
									<label>받은 날짜 : </label>
									<label id="lbDate02"></label>
								</li>
								<li>
									<div>
										<br><textarea name="smarteditor" readonly="readonly" id="smarteditor02" rows="10" cols="100" style="width: 460px; height: 330px;"></textarea>
									</div>
								</li>
							</ul>
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
												<td class="sendEmail" id="sendEmail">${rcv.send_email }</td>
												<td id="rcvCon">${rcv.note_con }</td>
												<td id="rcvDate"><fmt:formatDate value="${rcv.rcv_date }" pattern="yyyy-MM-dd"/></td>
												<td>${rcv.read_fl }</td>
												<td>${rcv.rcv_del_fl }</td>
												<td><a id="aTag" href="#layer1" class="btn-example1"></a></td>
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
												<td class="inquirynum" id="sendEmail">${send.rcv_email }</td>
												<td id="sendCon">${send.note_con }</td>
												<td id="sendDate"><fmt:formatDate value="${send.send_date }" pattern="yyyy-MM-dd"/></td>
												<td>${send.read_fl }</td>
												<td>${send.send_del_fl }</td>
												<td><a id="aTag02" href="#layer2" class="btn-example1"></a></td>
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