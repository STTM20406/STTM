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
		
		
		$(".tb_style_01").on("click", ".rcvTr #rcvCon", function(){
			var aTag = $('#aTag').attr('href');
			var email = $(this).siblings('#sendEmail').text();
			var con = $(this).text();
			var date = $(this).siblings('#rcvDate').text();
			
			var note_id = $(this).siblings("#note_id").text();
			
			console.log(note_id);
			
			console.log(aTag);
			
			$("#lbEmail").text(email);
			$("#smarteditor").val(con);
			$("#lbDate").text(date);
			$("#rcvEmaildInput").val(email);
			
			rcvNoteUpdateListPagination(note_id,1, 10);
			
			
			layer_popupup(aTag);
		})
		
		$(".tb_style_01").on("click",".sendTr #sendCon",function(){
			console.log("sendTr click :::::::::::::::::::::::");
			console.log("sendTr click");
			var aTag = $('#aTag02').attr('href');
			var email = $(this).siblings('#sendEmail').text();
			var con = $(this).text();
			var date = $(this).siblings('#sendDate').text();
			
			console.log(aTag);
			$("#lbEmail02").text(email);
			$("#smarteditor02").val(con);
			$("#lbDate02").text(date);
			
			
			
			layer_popupup(aTag);
		})
		
		$("#rcvBtn").on('click',function(){
			console.log("rcvBtn Click")
			$(".rcvFrm").attr("action","/rcvNoteWrite");
			$(".rcvFrm").attr("method","GET");
			$(".rcvFrm").submit();
		})
		
		$(".tb_style_01").on("click",".rcvTr #rcvDelBtn",function(){
			console.log("rcvDelBtn click");
		
			var noteConId = $(this).attr('name');
			console.log(noteConId);
			
			rcvDel(noteConId,1,10);
		})
		
		
	});
	
		function rcvDel(noteConId,page,pageSize){
			$.ajax({
				url : "/rcvDel",
				method : "post",
				data : "note_con_id=" + noteConId + "&page=" + page + "&pageSize=" + pageSize,
				success : function(data) {
					console.log("lllllllllllllllll")
					console.log(data);
					//사용자 리스트
					var hhtml = "";
					var html = "";
								
					
					//hhtml생성
					hhtml += "<tr>";
					hhtml += "<th>보낸 사람</th>";
					hhtml += "<th>내용</th>";
					hhtml += "<th>받은 날짜</th>";
					hhtml += "<th>쪽지 읽음 여부</th>";
					hhtml += "<th>삭제</th>";
					hhtml += "</tr>";
					
					data.rcvList.forEach(function(rcv, index){
							//html생성
							html += "<tr class='rcvTr' >";
							html += "<td class='sendEmail' id='sendEmail'>"+ rcv.send_email + "</td>";
							html += "<td style='display:none;' id='note_id'>"+rcv.note_con_id +"</td>";
							html += "<td id='rcvCon'>"+ rcv.note_con + "</td>";
							html += "<td id='rcvDate'>"+rcv.rcvDateStr+"</td>";	
							html += "<td>"+ rcv.read_fl + "</td>";
							html += "<td><button type='button' id='rcvDelBtn' name='"+rcv.note_con_id+"'>삭제</button></td>";
							html += "<td><a id='aTag' href='#layer1' class='btn-example1'></a></td>";
							html += "</tr>";
					});
					var pHtml = "";
					var pageVo = data.pageVo;
					console.log(data);
					console.log(pageVo);
					
					if(pageVo.page==1)
						pHtml += "<li class='disabled'><span>«<span></li>";
					else
						pHtml += "<li><a onclick='rcvNoteListPagination("+(pageVo.page-1)+", "+pageVo.pageSize+");' href='javascript:void(0);'>«</a></li>";
					
					for(var i =1; i <=data.rcvPaginationSize; i++){
						if(pageVo.page==i)
							pHtml += "<li class='active'><span>" + i + "</span></li>";
						else
							pHtml += "<li><a href='javascript:void(0);' onclick='rcvNoteListPagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
					}
					if(pageVo.page == data.rcvPaginationSize)
						pHtml += "<li class='disabled'><span>»<span></li>";
					else
						pHtml += "<li><a href='javascript:void(0);' onclick='rcvNoteListPagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
					
					$(".pagination").html(pHtml);
					$("#publicHeader").html(hhtml);
					$("#publicList").html(html);
				}
			})
		}
	
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
	
	
	   
	function rcvNoteUpdateListPagination(note_id,page, pageSize) {
		$.ajax({
			url : "/rcvNoteUpdateList",
			method : "post",
			data : "note_id="+note_id+"&page=" + page + "&pageSize="+ pageSize,
			success : function(data) {
				console.log(data);
				//사용자 리스트
				var hhtml = "";
				var html = "";
							
				
				//hhtml생성
				hhtml += "<tr>";
				hhtml += "<th>보낸 사람</th>";
				hhtml += "<th>내용</th>";
				hhtml += "<th>받은 날짜</th>";
				hhtml += "<th>쪽지 읽음 여부</th>";
				hhtml += "<th>삭제</th>";
				hhtml += "</tr>";
				
					
				data.rcvList.forEach(function(rcv, index){
					
				
					//html생성
					html += "<tr class='rcvTr' >";
					html += "<td class='sendEmail' id='sendEmail'>"+ rcv.send_email + "</td>";
					html += "<td style='display:none;' id='note_id'>"+rcv.note_con_id +"</td>";
					html += "<td id='rcvCon'>"+ rcv.note_con + "</td>";
					html += "<td id='rcvDate'>"+rcv.rcvDateStr+"</td>";	
					html += "<td>"+ rcv.read_fl + "</td>";
					html += "<td><button type='button' id='rcvDelBtn' name='"+rcv.note_con_id+"'>삭제</button></td>";
					html += "<td><a id='aTag' href='#layer1' class='btn-example1'></a></td>";
					html += "</tr>";
					
				});
				var pHtml = "";
				var pageVo = data.pageVo;
				console.log(data);
				console.log(pageVo);
				
				if(pageVo.page==1)
					pHtml += "<li class='disabled'><span>«<span></li>";
				else
					pHtml += "<li><a onclick='rcvNoteListPagination("+(pageVo.page-1)+", "+pageVo.pageSize+");' href='javascript:void(0);'>«</a></li>";
				
				for(var i =1; i <=data.rcvPaginationSize; i++){
					if(pageVo.page==i)
						pHtml += "<li class='active'><span>" + i + "</span></li>";
					else
						pHtml += "<li><a href='javascript:void(0);' onclick='rcvNoteListPagination("+ i + ", " + pageVo.pageSize+");'>"+i+"</a></li>";
				}
				if(pageVo.page == data.rcvPaginationSize)
					pHtml += "<li class='disabled'><span>»<span></li>";
				else
					pHtml += "<li><a href='javascript:void(0);' onclick='rcvNoteListPagination("+(pageVo.page+1)+", "+pageVo.pageSize+");'>»</a></li>";
				
				$(".pagination").html(pHtml);
				$("#publicHeader").html(hhtml);
				$("#publicList").html(html);
			}
		});
	}
	
</script>

<!-- 받은 쪽지 상세내용 팝업 -->
<form class="rcvFrm">
		<div id="layer1" class="pop-layer">
			<div class="pop-container">
				<div class="pop-note">
					<!--content //-->
					<input type="hidden" id="array" name="array">
					<div class="new_proejct">
						<h2>쪽지 내용</h2>
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
								<textarea name="smarteditor" readonly="readonly" id="smarteditor" class="noteEditor"></textarea>
							</li>
						</ul>
						<div class="prj_btn">
							<button type="button" id="rcvBtn" class="btn_style_01" style="width:70%; margin:0 auto"> 답장</button>
						</div>
						<div class="btn-r">
							<a href="#" class="btn-layerClose">Close</a>
						</div>
					</div>
				</div>
			</div>
		</div>
</form>
		
<!-- 보낸 쪽지 상세내용 팝업 -->
		<div id="layer2" class="pop-layer">
			<div class="pop-container">
				<div class="pop-note">
					<!--content //-->
						<input type="hidden" id="array" name="array">
						<div class="new_proejct">
							<h2>쪽지 내용</h2>
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
									<textarea name="smarteditor" readonly="readonly" id="smarteditor02" class="noteEditor"></textarea>
								</li>
							</ul>
						</div>
					<div class="btn-r">
						<a href="#" class="btn-layerClose">Close</a>
					</div>
				</div>
			</div>
		</div>
		
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
<section class="contents">
	<h2 class="contentTitle">받은 쪽지함</h2>
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
								<th>보낸 사람</th>
								<th>내용</th>
								<th>받은 날짜</th>
								<th>쪽지 읽음 여부</th>
								<th>삭제</th>
							</tr>
						</thead>
						<tbody id="publicList">
							<c:forEach items="${rcvList }" var="rcv">
							<tr class="rcvTr" >
								<td class="sendEmail" id="sendEmail">${rcv.send_email }</td>
								<td style="display:none;" id="note_id">${rcv.note_con_id }</td>
								<td id="rcvCon">${rcv.note_con }</td>
								<td id="rcvDate"><fmt:formatDate value="${rcv.rcv_date }" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>${rcv.read_fl }</td>
								<td><button type="button" id="rcvDelBtn" name="${rcv.note_con_id }">삭제</button></td>
								<td><a id="aTag" href="#layer1" class="btn-example1"></a></td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div class="pagination">
						<c:choose>
							<c:when test="${pageVo.page == 1 }">
								<a href="javascript:;" class="btn_first"></a>
							</c:when>
							<c:otherwise>
								<a href="/noteList?page=${pageVo.page-1}&pageSize=${pageVo.pageSize}">«</a>
							
							</c:otherwise>
						</c:choose>

						<c:forEach begin="1" end="${rcvPaginationSize}" var="i">
							<c:choose>
								<c:when test="${pageVo.page == i}">
									<span>${i}</span>
								</c:when>
								<c:otherwise>
								<a href="/noteList?page=${i}&pageSize=${pageVo.pageSize}">${i}</a>
								</c:otherwise>
							</c:choose>

						</c:forEach>

						<c:choose>
							<c:when test="${pageVo.page == rcvPaginationSize}">
								<a href="javascript:;"  class="btn_last"></a>
							</c:when>
							<c:otherwise>
							<a href="/noteList?page=&page=${pageVo.page + 1}&pageSize=${pageVo.pageSize}">»</a>

							</c:otherwise>
						</c:choose>
				
				</div>
			</div>
	</div>
</section>