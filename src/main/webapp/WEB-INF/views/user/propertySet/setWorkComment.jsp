<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.inquiryTr:hover {
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

		$('ul.tabs li').click(function() {
			var tab_id = $(this).attr('data-tab');

			$('ul.tabs li').removeClass('current');
			$('.tab-content').removeClass('current');

			$(this).addClass('current');
			$("#" + tab_id).addClass('current');
		});

		$(".commTr td .commUpdateBtn").on("click", function() {
			//console.log($(this));
 			var inq_id = $(this).parent().prev().prev().prev().text();
 			console.log(inq_id);
 			var a = inq_id.append("<input type=text id='changeInput' value="+inq_id+"/>");
 			console.log(a);
 			test(inq_id);
		})
		
		function test(inq_id){
			$.ajax({
				url:"/commUpdate",
				method:"post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
// 				data : a,
// 				success:function(a){
// 					console.log(a);
// 				}
			})
		}
		
		// 코멘트 삭제하기
		$(".tb_style_01 .commTr").on("click", ".commDeleteBtn", function(){
			console.log("commDeleteBtn click");
			console.log(this);
			var commPrj_id = $(this).siblings("input").val();
			console.log(commPrj_id);
			var commDelete = $(this).attr("name");
			console.log(commDelete);
			var comm_id = commDelete;
			
			commDeleteAjax(comm_id, commPrj_id);
		})
		
		function commDeleteAjax(comm_id, commPrj_id){
			$.ajax({
				url:"/commDelete",
				method:"get",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data:"prj_id=" + commPrj_id +"&comm_id="+comm_id,
				success:function(data){
					console.log(data);
					var html ="";
					data.data.forEach(function(Array,index){
						html += "<tr class='commTr'>";
						html += "<td class='commnum'>"+Array.comm_id+"</td>";
						html += "<td class='commCon'>";
						html +=	Array.comm_content;
						html +=	"<input type='hidden' name='commContent' value='"+ Array.comm_content +"'/>";
						html +=	"<input type='hidden' name='commComm_id' value='"+ Array.comm_id +"'/>";
						html +=	"<input type='hidden' name='commPrj_id' value='"+ Array.prj_id +"'/>";
						html += "</td>";
						html += "<td>"+Array.user_email+"</td>";
						html += "<td><button type='button' id='commUpdateBtn' class='commUpdateBtn'>수정</button></td>";
						html += "<td class='commDeleteTd'>";
						html +=	"<input type='hidden' id='tdCommPrj_id'value='"+Array.prj_id+"'/>";
						html +=	"<button type='button' id='commDeleteBtn' class='commDeleteBtn' name='"+Array.comm_id+"'>삭제</button>";
						html += "</td>";
						html += "</tr>";
					});
				$(".tb_style_01 tbody").html(html);
				}
				
				
			})	
		}
		
		// 댓글등록하기 버튼
		$("#replyBtn").on("click",function(){
			$("#frm02").attr("action","/comment");
			$("#frm02").attr("method","post");
			$("#frm02").submit();
		})
		
		
		
	});
</script>

<section class="contents">

	<div id="container">
	

		<div class="sub_menu">
			<ul class="tabs">
				<li data-tab="tab-1">속성</li>
				<li data-tab="tab-2">코멘트</li>
				<li data-tab="tab-3">파일&링크</li>
			</ul>
		</div>

		<div class="tab_con">
			<form id="frm01">
				<div id="tab-1" class="tab-content current">
				</div>
			</form>
			<form id="frm02">
				<div id="tab-2" class="tab-content">
				<div>
						<table class="tb_style_01">
							<colgroup>
								<col width="10%">
								<col width="20%">
								<col width="10%">
								<col width="5%">
								<col width="2%">
								<col width="2%">
							</colgroup>
							<tbody>
								<tr>
									<th>번호</th>
									<th>내용</th>
									<th>작성자 아이디</th>
									<th colspan='3'>작성일</th>
								</tr>

								<c:forEach items="${commentList }" var="comm">
									<c:choose>
										<c:when test="${comm.del_fl == 'N'}">

											<tr class="commTr">
												<td class="commnum">${comm.comm_id }</td>
												<td class="commCon">
													${comm.comm_content }
													<input type="hidden" name="commContent" value="${comm.comm_content }"/>
													<input type="hidden" name="commComm_id" value="${comm.comm_id }"/>
													<input type="hidden" name="commPrj_id" value="${comm.prj_id }"/>
												</td>
												<td>${comm.user_email }</td>
												<td><fmt:formatDate value="${comm.comm_date }" pattern="yyyy-MM-dd" /></td>
												<td><button type="button" id="commUpdateBtn" class="commUpdateBtn">수정</button></td>
												<td class="commDeleteTd">
													<input type="hidden" id="tdCommPrj_id"value="${comm.prj_id }"/>
													<button type="button" id="commDeleteBtn" class="commDeleteBtn" name="${comm.comm_id }">삭제</button>
												</td>
											</tr>
										</c:when>
									</c:choose>
								</c:forEach>

							</tbody>
						</table>
						<label>댓글 작성</label><br>
							<textarea rows="1" cols="60" name="comm_content"></textarea>
							<button type="button" name="replyBtn" id="replyBtn"> 댓글등록 </button>
					</div>
				</div>
			</form>
			<form id="frm03">
				<div id="tab-3" class="tab-content"></div>
			</form>
		</div>

	</div>

</section>