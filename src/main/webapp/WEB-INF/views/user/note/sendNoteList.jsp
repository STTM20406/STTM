<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		
		
		$(".tb_style_01").on("click",".rcvTr #rcvCon",function(){
			console.log("sendTr click :::::::::::::::::::::::");
			console.log("sendTr click");
			var aTag = $('#aTag').attr('href');
			var email = $(this).siblings('#sendEmail').text();
			var con = $(this).text();
			var date = $(this).siblings('#rcvDate').text();
			
			console.log(aTag);
			$("#lbEmail").text(email);
			$("#smarteditor").val(con);
			$("#lbDate").text(date);

			layer_popupup(aTag);
		})
	});
	
	   function layer_popupup(el){
	      console.log(el);

	        var $el = $(el);      //레이어의 id를 $el 변수에 저장
	        var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

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

<!-- 받은 쪽지 상세내용 팝업 -->
<form class="rcvFrm">
		<div id="layer1" class="pop-layer">
			<div class="pop-container">
				<div class="pop-project">
					<!--content //-->
						<input type="hidden" id="array" name="array">
						<div class="new_proejct">
							<ul>
								<li>
									<label>받는사람 : </label>
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
						<a href="#" class="btn-layerClose">Close</a>
					</div>
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
				</div>
			</div>
		</div>

<section class="contents">
	
	<div id="container">

		<div class="sub_menu">
			<ul class="sub_menu_item">
				<li>
					<a href="/noteList">받은 쪽지함</a>
				</li>
				<li>
					<a href="/sendNoteList">보낸 쪽지함</a>
				</li>
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
						<thead id="publicHeader">
							<tr>
								<th>받는 사람</th>
								<th>내용</th>
								<th>보낸 날짜</th>
								<th>쪽지 읽음 여부</th>
							</tr>
						</thead>
						<tbody id="publicList">
								<c:forEach items="${sendList }" var="send">
											<tr class="rcvTr" >
												<td class="sendEmail" id="sendEmail">${send.rcv_email }</td>
												<td id="rcvCon">${send.note_con }</td>
												<td style="display:none;" id="note_id">${send.note_con_id }</td>
												<td id="rcvDate"><fmt:formatDate value="${send.send_date }" pattern="yyyy-MM-dd HH:mm"/></td>
												<td>${send.read_fl }</td>
												<td><a id="aTag" href="#layer1" class="btn-example1"></a></td>
												
											</tr>
								</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="pagination">
						<c:choose>
							<c:when test="${pageVo.page == 1 }">
								<a class="btn_first"></a>
							</c:when>
							<c:otherwise>
								<a href="/sendNoteList?page=${pageVo.page-1}&pageSize=${pageVo.pageSize}">«</a>
							
							</c:otherwise>
						</c:choose>

						<c:forEach begin="1" end="${sendPaginationSize}" var="i">
							<c:choose>
								<c:when test="${pageVo.page == i}">
									<span>${i}</span>
								</c:when>
								<c:otherwise>
								<a href="/sendNoteList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
								</c:otherwise>
							</c:choose>

						</c:forEach>

						<c:choose>
							<c:when test="${pageVo.page == sendPaginationSize}">
								<a class="btn_last"></a>
							</c:when>
							<c:otherwise>
							<a href="/sendNoteList?page=&page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>
								

							</c:otherwise>
						</c:choose>
				
				</div>
			</div>
	</div>

	</div>
</section>