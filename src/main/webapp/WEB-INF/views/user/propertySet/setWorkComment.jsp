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

		$(".inquiryTr").on("click", function() {
			console.log("inquiryTr click");
			var inq_id = $(this).find(".inquirynum").text();
			$("#inq_id").val(inq_id);

			$("#frm").submit();
		})
		
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
								<col width="40%">
								<col width="30%">
								<col width="10%">
								<col width="10%">
							</colgroup>
							<tbody>
								<tr>
									<th>번호</th>
									<th>내용</th>
									<th>작성자 아이디</th>
									<th>작성일</th>
								</tr>

								<c:forEach items="${commentList }" var="comm">
									<c:choose>
										<c:when test="${comm.del_fl == 'N'}">

											<tr class="inquiryTr">
												<td class="inquirynum">${comm.comm_id }</td>
												<td>${comm.comm_content }</td>
												<td>${comm.user_email }</td>
												<td><fmt:formatDate value="${comm.comm_date }" pattern="yyyy-MM-dd" /></td>
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